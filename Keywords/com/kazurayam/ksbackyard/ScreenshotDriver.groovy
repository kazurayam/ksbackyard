package com.kazurayam.ksbackyard

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import javax.imageio.ImageIO

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kazurayam.imagedifference.ImageDifference
import com.kazurayam.imagedifference.ImageDifferenceSerializer
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import groovy.json.JsonOutput
import net.coobird.thumbnailator.Thumbnails
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.coordinates.Coords
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.shooting.ShootingStrategies


/**
 * Wraps the AShot API, WebDriver Screenshot utility. 
 * Provides some add-on features used in "Visual Testing in Katalon Studio"
 * 
 * @author kazurayam
 */
class ScreenshotDriver {

	static private Boolean forceSnapshots_ = false
	static private int DEFAULT_SCROLLING_TIMEOUT = 500

	static void setForceSnapshots(Boolean wanted) {
		forceSnapshots_ = wanted
	}

	static Path tmpDir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp')

	/**
	 * takes screenshot of the specified WebElement in the target WebPage,
	 * returns it as a BufferedImage object.
	 * 
	 * If the specified webElement is not found, then screenshot of whole page
	 * will be returned.
	 * 
	 * @param webDriver
	 * @param webElement
	 * @return BufferedImage
	 */
	static BufferedImage takeElementImage(WebDriver webDriver, WebElement webElement) {
		Screenshot screenshot = new AShot().
				coordsProvider(new WebDriverCoordsProvider()).
				shootingStrategy(ShootingStrategies.viewportPasting(DEFAULT_SCROLLING_TIMEOUT)).
				takeScreenshot(webDriver, webElement)
		return screenshot.getImage()
	}


	/**
	 * provides the same function as takeElementImage(WebDriver, WebElement).
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @param testObject
	 * @return
	 */
	@Keyword
	static BufferedImage takeElementImage(TestObject testObject) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		WebElement webElement = WebUI.findWebElement(testObject, 30)
		return takeElementImage(webDriver, webElement)
	}


	/**
	 * takes screenshot of the specified WebElement in the target WebPage,
	 * and save it into the output file in PNG format.
	 *
	 * @param webDriver
	 * @param webElement
	 * @param file
	 */
	static void saveElementImage(WebDriver webDriver, WebElement webElement, File file) {
		BufferedImage image = takeElementImage(webDriver, webElement)
		ImageIO.write(image, "PNG", file)
	}


	/**
	 * provides the same function as saveElementImage(WebDriver, WebElement, File)
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @param testObject
	 * @param file
	 */
	@Keyword
	static void saveElementImage(TestObject testObject, File file) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		WebElement webElement = WebUI.findWebElement(testObject,30)
		saveElementImage(webDriver, webElement, file)
	}

	//-----------------------------------------------------------------

	/**
	 * takes screenshot of the entire page 
	 * while ignoring some elements specified
	 * returns it as a BufferedImage object
	 * 
	 * @param webDriver
	 * @param ignoredElementList 
	 * @return BufferedImage
	 */
	static BufferedImage takeEntirePageImage(WebDriver webDriver, Options options)
	{
		int timeout = options.getTimeout()
		List<By> byList = TestObjectSupport.toBy(options.getIgnoredElements())
		AShot aShot = new AShot().
				coordsProvider(new WebDriverCoordsProvider()).
				shootingStrategy(ShootingStrategies.viewportPasting(timeout))
		for (By by : byList) {
			aShot = aShot.addIgnoredElement(by)
			println "added ignored element ${by}"
		}
		Screenshot screenshot = aShot.takeScreenshot(webDriver)

		// paint specific web elements in the page with grey color
		BufferedImage censored = censor(screenshot)

		BufferedImage result
		// if required, resize the image to make its byte-size smaller
		println "options.getWidth() is ${options.getWidth()}"
		if (options.getWidth() > 0) {
			result = resize(censored, options.getWidth())
		} else {
			result = censored
		}
		return result
	}

	// In which color should we paint WebElements to ignore
	static Color PAINT_IT_COLOR = Color.LIGHT_GRAY

	/**
	 * censor means 検閲 in Japanese.
	 * 
	 */
	static BufferedImage censor(Screenshot screenshot) {
		BufferedImage bi = screenshot.getImage()
		Graphics2D g2D = bi.createGraphics()
		g2D.setColor(PAINT_IT_COLOR)
		Set<Coords> paintedAreas = screenshot.getIgnoredAreas()
		for (Coords rect : paintedAreas) {
			int x = (int)rect.getX()
			int y = (int)rect.getY()
			int width = (int)rect.getWidth()
			int height = (int)rect.getHeight()
			g2D.fillRect(x, y, width, height)
		}
		return bi
	}

	/**
	 * Resize the source image to have the given width while retaining the aspect ratio unchanged
	 * 
	 * This method utilizes the Thumbnailator library (https://github.com/coobird/thumbnailator)
	 * 
	 * @param sourceImage raw Sreenshot image
	 * @param targetWidth resize the sourceImage to this width, retaining the aspect ratio unchanged
	 * @return resized image
	 */
	static BufferedImage resize(BufferedImage sourceImage, int targetWidth) {
		if (targetWidth < 0) return sourceImage
		int sourceWidth  = sourceImage.getWidth()
		int sourceHeight = sourceImage.getHeight()
		int targetHeight = (int)Math.round((sourceHeight * targetWidth * 1.0) / sourceWidth)
		BufferedImage targetBI = Thumbnails.of(sourceImage).
				size(targetWidth, targetHeight).
				asBufferedImage()
		return targetBI
	}

	/**
	 * takes screenshot of the entire page targeted,
	 * returns it as a BufferedImage object
	 *
	 * @param webDriver
	 * @param webElement
	 * @param timeout millisecond, wait for page to be displayed stable after scrolling downward
	 * @return BufferedImage
	 */
	static BufferedImage takeEntirePageImage(WebDriver webDriver, Integer timeout = DEFAULT_SCROLLING_TIMEOUT)
	{
		Screenshot screenshot = new AShot().
				coordsProvider(new WebDriverCoordsProvider()).
				shootingStrategy(ShootingStrategies.viewportPasting(timeout)).
				takeScreenshot(webDriver)
		return screenshot.getImage()
	}

	//--------------

	/**
	 * 
	 * @param options
	 * @return
	 */
	@Keyword
	static BufferedImage takeEntirePageImage(Options options) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		return takeEntirePageImage(webDriver, options)
	}

	/**
	 * provides the same function as takeEntirePageImage(WebDriver, Integer)
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @timeout millisecond, wait for page to displayed stable after scrolling downward
	 * @return
	 */
	@Keyword
	static BufferedImage takeEntirePageImage(Integer timeout = DEFAULT_SCROLLING_TIMEOUT)
	{
		WebDriver webDriver = DriverFactory.getWebDriver()
		return takeEntirePageImage(webDriver, timeout)
	}

	//-------------

	static void saveEntirePageImage(WebDriver webDriver, File file, Options options)
	{
		BufferedImage image = takeEntirePageImage(webDriver, options)
		ImageIO.write(image, "PNG", file)
	}

	/**
	 * take the screenshot of the entire page targeted,
	 * and save it into the output file in PNG format.
	 *
	 * @param webDriver
	 * @param webElement
	 * @param output
	 */
	static void saveEntirePageImage(WebDriver webDriver, File file, Integer timeout = DEFAULT_SCROLLING_TIMEOUT)
	{
		BufferedImage image = takeEntirePageImage(webDriver, timeout)
		ImageIO.write(image, "PNG", file)
	}

	//-------------

	/**
	 * 
	 * @param file
	 * @param options
	 */
	@Keyword
	static void saveEntirePageImage(File file, Options options) {
		WebDriver driver = DriverFactory.getWebDriver()
		saveEntirePageImage(driver, file, options)
	}

	/**
	 * provides the same function as saveEntirePageImage(WebDriver, File, Integer)
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @param file
	 */
	@Keyword
	static void saveEntirePageImage(File file, Integer timeout = DEFAULT_SCROLLING_TIMEOUT)
	{
		WebDriver driver = DriverFactory.getWebDriver()
		saveEntirePageImage(driver, file, timeout)
	}

	//-----------

	/**
	 * similar to saveEntirePageImage(WebDriver, File, Integer)
	 * 
	 * @deprecated use saveEntirePageImage(File, Integer) instead
	 * 
	 * @param webDriver
	 * @param file
	 */
	static void takeEntirePage(WebDriver webDriver, File file, Integer timeout = DEFAULT_SCROLLING_TIMEOUT)
	{
		saveEntirePageImage(webDriver, file, timeout)
	}

	//-----------------------------------------------------------------

	/**
	 * @return timestamp string of now in the format yyyyMMdd_HHmmss
	 */
	public static getTimestampNow()
	{
		ZonedDateTime now = ZonedDateTime.now()
		return DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(now)
	}



	/**
	 * 
	 */
	static class Options {

		static private int DEFAULT_SCROLLING_TIMEOUT = 500
		static private int MAXIMUM_IMAGE_WIDTH = 4000

		private int timeout
		private List<TestObject> ignoredElements
		private int width

		static class Builder {

			private int timeout
			private List<TestObject> ignoredElements
			private int width

			Builder() {
				timeout = DEFAULT_SCROLLING_TIMEOUT
				ignoredElements = new ArrayList<TestObject>()   // no elements to ignore
				width = -1  // not specified
			}
			/**
			 * set scrolling timeout 
			 * @param value in millisecond. Optional. Defaults to 500 milli seconds
			 * @return
			 */
			Builder timeout(int value) {
				if (value < 0) {
					throw new IllegalArgumentException("value(${value}) must not be negative")
				}
				if (value > DEFAULT_SCROLLING_TIMEOUT * 10) {
					throw new IllegalArgumentException("value(${value}) must be less than " +
					"or equal to ${DEFAULT_SCROLLING_TIMEOUT * 10} milli-seconds.")
				}
				this.timeout = value
				return this
			}
			Builder addIgnoredElement(TestObject testObject) {
				Objects.requireNonNull(testObject, "testObject must not be null")
				this.ignoredElements.add(testObject)
				return this
			}
			Builder width(int value) {
				if (value <= 0) {
					throw new IllegalArgumentException("value(${value}) must not be negative or equal to 0")
				}
				if (value > MAXIMUM_IMAGE_WIDTH) {
					throw new IllegalArgumentException("value(${value}) must be less than or equal to ${MAXIMUM_IMAGE_WIDTH}")
				}
				this.width = value
				return this
			}
			Options build() {
				return new Options(this)
			}
		}

		private Options(Builder builder) {
			this.timeout = builder.timeout
			this.ignoredElements = builder.ignoredElements
			this.width = builder.width
		}

		int getTimeout() {
			return this.timeout
		}

		List<TestObject> getIgnoredElements() {
			return this.ignoredElements
		}

		int getWidth() {
			return this.width
		}

		@Override
		String toString() {
			String s = JsonOutput.toJson(this)
			String pp = JsonOutput.prettyPrint(s)
			return pp
		}
	}

}
