package com.kazurayam.ksbackyard

import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import javax.imageio.ImageIO

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.shooting.ShootingStrategies

// import com.kazurayam.ksbackyard.test.ashot.AShotMock

/**
 * Wraps the AShot API, WebDriver Screenshot utility. 
 * Provides some add-on features used in "Visual Testing in Katalon Studio"
 * 
 * @author kazurayam
 *
 */
class ScreenshotDriver {

	static public Path snapshotsDir

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
	@Keyword
	static BufferedImage takeElementImage(WebDriver webDriver, WebElement webElement) {
		int timeout = 500
		Screenshot screenshot = new AShot().
				coordsProvider(new WebDriverCoordsProvider()).
				shootingStrategy(ShootingStrategies.viewportPasting(timeout)).
				takeScreenshot(webDriver, webElement)
		return screenshot.getImage()
	}


	/**
	 * This method is solely for DEBUGGING purpose.
	 * This method calls com.kazurayam.ksbackyard.test.ashot.AshotMock class which is a copy of real AShot.
	 * We will insert print statements to investigate the behavior of AShot.
	 * 
	 * @param webDriver
	 * @param webElement
	 * @return
	 *
	 @Keyword
	 static BufferedImage takeElementImage_mock(WebDriver webDriver, WebElement webElement) {
	 int timeout = 500
	 Screenshot screenshot = new AShotMock().
	 coordsProvider(new WebDriverCoordsProvider()).
	 shootingStrategy(ShootingStrategies.viewportPasting(timeout)).
	 takeScreenshot(webDriver, webElement)
	 return screenshot.getImage()
	 }
	 */

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

	static BufferedImage takeElementImage_mock(TestObject testObject) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		WebElement webElement = WebUI.findWebElement(testObject, 30)
		return takeElementImage_mock(webDriver, webElement)
	}

	/**
	 * takes screenshot of the specified WebElement in the target WebPage,
	 * and save it into the output file in PNG format.
	 *
	 * @param webDriver
	 * @param webElement
	 * @param file
	 */
	@Keyword
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





	/**
	 * takes screenshot of the entire page targeted,
	 * returns it as a BufferedImage object
	 *
	 * @param webDriver
	 * @param webElement
	 * @param timeout millisecond, wait for page to be displayed stable after scrolling downward
	 * @return BufferedImage
	 */
	@Keyword
	static BufferedImage takeEntirePageImage(WebDriver webDriver, Integer timeout = 300) {
		Screenshot screenshot = new AShot().
				shootingStrategy(ShootingStrategies.viewportPasting(timeout)).
				takeScreenshot(webDriver)
		return screenshot.getImage()
	}


	/**
	 * provides the same function as takeEntirePageImage(WebDriver, Integer)
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @timeout millisecond, wait for page to displayed stable after scrolling downward
	 * @return
	 */
	@Keyword
	static BufferedImage takeEntirePageImage(Integer timeout = 300) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		return takeEntirePageImage(webDriver, timeout)
	}

	/**
	 * take the screenshot of the entire page targeted,
	 * and save it into the output file in PNG format.
	 *
	 * @param webDriver
	 * @param webElement
	 * @param output
	 */
	@Keyword
	static void saveEntirePageImage(WebDriver webDriver, File file, Integer timeout = 300) {
		BufferedImage image = takeEntirePageImage(webDriver, timeout)
		ImageIO.write(image, "PNG", file)
	}

	/**
	 * provides the same function as saveEntirePageImage(WebDriver, File, Integer)
	 * The WebDriver object is resolved by calling DriverFactory.getWebDriver()
	 * 
	 * @param file
	 */
	@Keyword
	static void saveEntirePageImage(File file, Integer timeout = 300) {
		WebDriver driver = DriverFactory.getWebDriver()
		saveEntirePageImage(driver, file, timeout)
	}



	/**
	 * similar to saveEntirePageImage(WebDriver, File, Integer)
	 * @deprecated use saveEntirePageImage(File, Integer) instead
	 * @param webDriver
	 * @param file
	 */
	@Keyword
	static void takeEntirePage(WebDriver webDriver, File file, Integer timeout = 300) {
		saveEntirePageImage(webDriver, file, timeout)
	}











	/**
	 * @deprecated use compareImages(BufferedImage, BufferedImage, Double)
	 * @param expectedImage
	 * @param actualImage
	 * @param criteriaPercent
	 * @return
	 */
	@Keyword
	static ImageDifference verifyImages(BufferedImage expectedImage,
			BufferedImage actualImage, Double criteriaPercent) {
		return compareImages(expectedImage, actualImage, criteriaPercent)
	}

	/**
	 * compare 2 images, calculate the magnitude of difference between the two
	 * 
	 * @param BufferedImage expectedImage
	 * @param BufferedImage actualImage
	 * @param Double criteriaPercentage, e.g. 90.0%
	 * @return ImageDifference object which represents how much different the input 2 images are
	 */
	@Keyword
	static ImageDifference compareImages(
			BufferedImage expectedImage,
			BufferedImage actualImage,
			Double criteriaPercent) {
		ImageDifference difference =
				new ImageDifference(expectedImage, actualImage)
		difference.setCriteria(criteriaPercent)
		return difference
	}

	/**
	 * @param expectedImage of java.io.File prepared beforehand using saveElementImage(File) method
	 * @param actualImage of TestObject which points HTML element in question
	 * @return ImageDifference object which contains comparison result
	 */
	@Keyword
	static ImageDifference compareImages(
			File expected,
			TestObject actual,
			Double criteriaPercent) {
		BufferedImage exp = ImageIO.read(expected)
		BufferedImage act = takeElementImage(actual)
		ImageDifference imgDifference = compareImages(exp, act, criteriaPercent)
		return imgDifference
	}

	/**
	 * @param expectedImage of java.io.File prepared beforehand using saveElementImage(File) method
	 * @param actualImage of TestObject which points HTML element in question
	 * @return ImageDifference object which contains comparison result
	 */
	@Keyword
	static ImageDifference compareImages(
			TestObject expected,
			TestObject actual,
			Double criteriaPercent) {
		BufferedImage exp = takeElementImage(expected)
		BufferedImage act = takeElementImage(actual)
		ImageDifference imgDifference = compareImages(exp, act, criteriaPercent)
		return imgDifference
	}

	/**
	 * Compare 2 images, expected one is read from file, actual one is cropped from web page,
	 * and check if images are SIMILAR enough.
	 * When failed, the actual image is saved into file of which path is shown in the error message.
	 * 
	 * @param expectedImage of java.io.File prepared beforehand using saveElementImage(File) method
	 * @param actualImage of TestObject which points HTML element in question
	 * @return true if expectedImage and actualImage are similar enough; difference ratio < criteriaPercent
	 */
	@Keyword
	static Boolean verifyImagesAreSimilar(
			File expected,
			TestObject actual,
			Double criteriaPercent = 5.0,
			FailureHandling flowControl = FailureHandling.CONTINUE_ON_FAILURE) {
		ImageDifference imgDifference = compareImages(expected, actual, criteriaPercent)
		boolean result = imgDifference.imagesAreSimilar()
		FileTrio fileTrio
		if (!result) {
			fileTrio = saveImageSnapshots(imgDifference, 'verifyImagesAreSimilar(File,TestObject)')
		}
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"images are expected to be similar but are different," +
				" difference=${imgDifference.getRatioAsString()}%," +
				" the expected image is located in the file ${fileTrio.getExpected().toString()}," +
				" the actual image was saved into file ${fileTrio.getActual().toString()}",
				result, flowControl)
		return result
	}

	/**
	 * Compare 2 images, expected one is read from file, actual one is cropped from web page,
	 * and check if images are DIFFERENT enough.
	 * When failed, the actual image is saved into file of which path is shown in the error message.
	 * 
	 * @param expectedImage of java.io.File prepared beforehand using saveElementImage(File) method
	 * @param actualImage of TestObject which points HTML element in question
	 * @return true if expecteImage and actualImage are different enough; differenece ratio > criteriaPercent
	 */
	@Keyword
	static Boolean verifyImagesAreDifferent(
			File expected,
			TestObject actual,
			Double criteriaPercent = 5.0,
			FailureHandling flowControl = FailureHandling.CONTINUE_ON_FAILURE) {
		ImageDifference imgDifference = compareImages(expected, actual, criteriaPercent)
		boolean result = imgDifference.imagesAreDifferent()
		FileTrio trio
		if (!result) {
			trio = saveImageSnapshots(imgDifference, 'verifyImagesAreDifferent(File,TestObject)')
		}
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"images are expected to be different but are similar," +
				" difference=${imgDifference.getRatioAsString()}%," +
				" the expected image is located in the file ${trio.getExpected().toString()}," +
				" the actual image was saved into file ${trio.getActual().toString()}",
				result, flowControl)
		return result
	}


	@Keyword
	static Boolean verifyImagesAreSimilar(
			TestObject expected,
			TestObject actual,
			Double criteriaPercent = 10.0,
			FailureHandling flowControl = FailureHandling.CONTINUE_ON_FAILURE) {
		ImageDifference imgDifference = compareImages(expected, actual, criteriaPercent)
		// check if these are similar?
		boolean result = imgDifference.imagesAreSimilar()
		FileTrio trio
		if (!result) {
			trio = saveImageSnapshots(imgDifference, 'verifyImagesAreSimilar(TestObject,TestObject)')
		}
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"images are expected to be similar but different, " +
				" difference=${imgDifference.getRatioAsString()}%," +
				" the expected image was saved into file ${trio.getExpected().toString()} " +
				" the actual image was saved into file ${trio.getActual().toString()}",
				result, flowControl)
		return result
	}

	@Keyword
	static Boolean verifyImagesAreDifferent(
			TestObject expected,
			TestObject actual,
			Double criteriaPercent = 10.0,
			FailureHandling flowControl = FailureHandling.CONTINUE_ON_FAILURE) {
		ImageDifference imgDifference = compareImages(expected, actual, criteriaPercent)
		// check if these are different?
		boolean result = imgDifference.imagesAreDifferent()
		FileTrio trio
		if (!result) {
			trio = saveImageSnapshots(imgDifference, 'verifyImagesAreSimilar(TestObject,TestObject)')
		}
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"images are expected to be different but similar. " +
				" difference=${imgDifference.getRatioAsString()}%," +
				" the expected image was saved into file ${trio.getExpected().toString()} " +
				" the actual image was saved into file ${trio.getActual().toString()}",
				result, flowControl)
		return result
	}

	/**
	 * utility method to save snapshot of the image
	 */
	private static FileTrio saveImageSnapshots(ImageDifference imgDifference, String identifier) {
		FileTrio trio = new FileTrio()
		//
		File expectedSnapshot = resolveSnapshotFile(identifier + ".expected")
		ImageIO.write(imgDifference.getActualImage(), "PNG", expectedSnapshot)
		trio.setExpected(expectedSnapshot)
		//
		File actualSnapshot = resolveSnapshotFile(identifier + ".actual")
		ImageIO.write(imgDifference.getActualImage(), "PNG", actualSnapshot)
		trio.setActual(actualSnapshot)
		//
		File diffSnapshot = resolveSnapshotFile(identifier + ".diff(${imgDifference.getRatioAsString()})")
		ImageIO.write(imgDifference.getActualImage(), "PNG", diffSnapshot)
		trio.setDiff(diffSnapshot)
		//
		return trio
	}

	private static File resolveSnapshotFile(String identifier) {
		Path parent
		if (snapshotsDir == null) {
			snapshotsDir = Paths.get(RunConfiguration.getProjectDir()).resolve("tmp").resolve("ScreenshotDriver-snapshots")
		}
		parent = snapshotsDir.resolve("${getTimestampNow()}")
		parent.toFile().mkdirs()
		return parent.resolve("${identifier}.png").toFile()
	}

	/**
	 * @return timestamp string of now in the format yyyyMMdd_HHmmss
	 */
	private static getTimestampNow() {
		ZonedDateTime now = ZonedDateTime.now()
		return DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(now)
	}

	/**
	 * encloses 3 File objects; expected, actual and diff
	 * 
	 * @author kazurayam
	 *
	 */
	static class FileTrio {
		private File expected_
		private File actual_
		private File diff_
		void setExpected(File expected) {
			this.expected_ = expected
		}
		void setActual(File actual) {
			this.actual_ = actual
		}
		void setDiff(File diff) {
			this.diff_ = diff
		}
		File getExpected() {
			return expected_
		}
		File getActual() {
			return actual_
		}
		File getDiff() {
			return diff_
		}
	}

}
