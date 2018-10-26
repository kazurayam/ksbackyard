package com.kazurayam.ksbackyard.test
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kazurayam.ksbackyard.ScreenshotDriver.ImageDifference
import com.kms.katalon.core.configuration.RunConfiguration

class ScreenshotDriverCompareTest {

	private static WebDriver driver_
	private static String url_ = "https://forum.katalon.com/discussions"
	private static Path workdir_

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		driver_ = WebDriverFactory.createWebDriver()
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverCompareTest')
		Files.createDirectories(workdir_)
	}

	@Before
	void before() {
	}

	@After
	void after() {}

	/*
	 * close browser
	 */
	@AfterClass
	static void afterClass() {
		Thread.sleep(1000)
		driver_.quit()
	}

	@Test
	void test_saveKazurayamProfilePicture() {
		String url = 'https://forum.katalon.com/discussion/9799/visual-testing-in-katalon-studio'
		driver_.get(url)
		WebDriverWait wait = new WebDriverWait(driver_, 20)
		String xpath = "//div[@id='Body']//div[contains(@class,'CommentsWrap')]//ul[contains(@class,'Comments')]/li[6]//img[contains(@class, 'ProfilePhoto')]"
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
		WebElement photo = driver_.findElement(By.xpath(xpath))
		scrollToElement(driver_, photo)
		Path output = workdir_.resolve("kazurayam.png")
		println ">>> test_saveKazurayamProfilePicture is calling ScreenshotDriver"
		BufferedImage bi = ScreenshotDriver.takeElementImage(driver_, photo)
		ImageIO.write(bi, "PNG", output.toFile())
		assertTrue("${output.toString()} does not exist", Files.exists(output))
	}

	private void scrollToElement(WebDriver driver, WebElement el) {
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].scrollIntoView(true);", el);
		}
	}
}
