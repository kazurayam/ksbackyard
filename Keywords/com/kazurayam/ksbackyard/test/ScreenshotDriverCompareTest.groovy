package com.kazurayam.ksbackyard.test

import java.awt.image.BufferedImage

import javax.imageio.ImageIO
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import static org.junit.Assert.fail

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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

import com.kazurayam.ksbackyard.TestObjectSupport
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
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverCompareTest')
		Files.createDirectories(workdir_)
		WebUI.openBrowser('')
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
		WebUI.closeBrowser()
	}

	@Test
	void test_saveProfilePicture() {
		String url = 'https://forum.katalon.com/discussion/9799/visual-testing-in-katalon-studio'
		String xpathStr = "//div[@id='Body']//div[contains(@class,'CommentsWrap')]" +
				"//ul[contains(@class,'Comments')]/li[2]" +
				"//div[contains(@class,'CommentHeader')]" +
				"//img[contains(@class,'ProfilePhoto')]"

		WebUI.navigateToUrl(url)
		TestObject tObj = new TestObject()
		tObj.addProperty("xpath", ConditionType.EQUALS, xpathStr, true)
		boolean result = WebUI.verifyElementPresent(tObj, 20)
		if (result) {
			//WebUI.scrollToElement(tObj, 10)
			Path output = workdir_.resolve("kazurayam.png")
			BufferedImage bi = ScreenshotDriver.takeElementImage_mock(tObj)
			ImageIO.write(bi, "PNG", output.toFile())
			assertTrue("${output.toString()} does not exist", Files.exists(output))
		} else {
			fail("Element is not present at ${xpathStr}")
		}
	}
}
