package com.kazurayam.ksbackyard

import static org.junit.Assert.assertTrue
import static org.junit.Assert.fail

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import org.apache.commons.io.FileUtils
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test

import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class ScreenshotDriverCompareImagesTest {

	private static String url_
	private static Path workdir_

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path targetHTML = projectDir.resolve('src/test/resources/fixture/Visual Testing in Katalon Studio - Katalon Q&A.html')
		url_ = targetHTML.toUri().toURL().toExternalForm()
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverCompareImagesTest')
		if (Files.exists(workdir_)) {
			FileUtils.deleteDirectory(workdir_.toFile())
		}
		Files.createDirectories(workdir_)
		WebUI.openBrowser('')
		//
		ScreenshotDriver.setForceSnapshots(true)
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


	/**
	 * Test saving a profile picture of mine from the URL
	 * 'https://forum.katalon.com/discussion/9799/visual-testing-in-katalon-studio'
	 * The page is tall. The picture is located far downward in the page.
	 * When I tried, the ScreenshotDriver#takeElementImage(TestObect) was not working well.
	 * It took the viewport screenshot of the URL rather than the small profile photo.
	 * By debugging I found that the ScreenshotDriver#takeElementImage(WebDriver, WebElement, Integer) method
	 * must be changed so that it takes entire-page screenshot first and crop the targeted picture out of the
	 * entire-page image.
	 * Them method has been modified, and now this test runs successful.
	 * @date 27 Oct 2018
	 * @author kazurayam
	 */
	@Test
	void test_saveProfilePicture() {
		WebUI.comment(">>> test_saveProfilePicture started")
		String xpathStr = makeXPathToPhoto(6)
		WebUI.navigateToUrl(url_)
		TestObject tObj = new TestObject()
		tObj.addProperty("xpath", ConditionType.EQUALS, xpathStr, true)
		boolean result = WebUI.verifyElementPresent(tObj, 20)
		if (result) {
			//WebUI.scrollToElement(tObj, 10)
			Path output = workdir_.resolve("kazurayam.png")
			BufferedImage bi = ScreenshotDriver.takeElementImage(tObj)
			ImageIO.write(bi, "PNG", output.toFile())
			assertTrue("${output.toString()} does not exist", Files.exists(output))
			int expectedLength = 20_000
			assertTrue("output.toFile().length()=${output.toFile().length()} is larger than expected ${expectedLength}",
					output.toFile().length() < expectedLength)
		} else {
			fail("Element is not present at ${xpathStr}")
		}
	}

	/**
	 * This test case method calls ScreeshotDriver.takeElementImage_mock(TestObject) method which
	 * indirectly calls com.kazurayam.ksbackyard.test.ashot.AShotMock's method.
	 * I inserted debug-print statements into AShotMock to investicate the behavior of the AShot class.
	 *
	 @Ignore
	 @Test
	 void test_saveProfilePicture_byMock() {
	 String xpathStr = makeXPathToPhoto(6)
	 WebUI.navigateToUrl(url_)
	 TestObject tObj = new TestObject()
	 tObj.addProperty("xpath", ConditionType.EQUALS, xpathStr, true)
	 boolean result = WebUI.verifyElementPresent(tObj, 20)
	 if (result) {
	 //WebUI.scrollToElement(tObj, 10)
	 Path output = workdir_.resolve("kazurayam_mock.png")
	 BufferedImage bi = ScreenshotDriver.takeElementImage_mock(tObj)
	 ImageIO.write(bi, "PNG", output.toFile())
	 assertTrue("${output.toString()} does not exist", Files.exists(output))
	 } else {
	 fail("Element is not present at ${xpathStr}")
	 }
	 }
	 */

	@Test
	void test_verifyImagesAreSimilar() {
		WebUI.comment(">>> test_verifyImagesAreSimilar started")
		File expectedImage = Paths.get(RunConfiguration.getProjectDir(),
				'src/test/resources/fixture/images/kazurayam.png').toFile()
		String xpathStr = makeXPathToPhoto(6)
		TestObject actualImage = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathStr, true)
		Double criteriaPercent = 5.0
		WebUI.navigateToUrl(url_)
		WebUI.verifyElementPresent(actualImage, 20)
		boolean result = ScreenshotDriver.verifyImagesAreSimilar(
				expectedImage,
				actualImage,
				criteriaPercent,
				workdir_.toFile(),
				FailureHandling.OPTIONAL)
	}

	@Ignore
	@Test
	void test_verifyImagesAreSimilar_failingCase() {
		WebUI.comment(">>> test_verifyImagesAreSimilar_failingCase started")
		File expectedImage = Paths.get(RunConfiguration.getProjectDir(),
				'src/test/resources/fixture/images/kazurayam.png').toFile()
		String xpathStr = makeXPathToPhoto(1)
		TestObject actualImage = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathStr, true)
		Double criteriaPercent = 5.0
		WebUI.navigateToUrl(url_)
		WebUI.verifyElementPresent(actualImage, 20)
		boolean result = ScreenshotDriver.verifyImagesAreSimilar(
				expectedImage,
				actualImage,
				criteriaPercent,
				workdir_.toFile(),
				FailureHandling.OPTIONAL)
	}

	@Test
	void test_verifyImagesAreDifferent() {
		WebUI.comment(">>> test_verifyImagesAreDifferent started")
		File expectedImage = Paths.get(RunConfiguration.getProjectDir(),
				'src/test/resources/fixture/images/andrej.png').toFile()
		String xpathStr = makeXPathToPhoto(6)
		TestObject actualImage = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathStr, true)
		Double criteriaPercent = 5.0
		WebUI.navigateToUrl(url_)
		WebUI.verifyElementPresent(actualImage, 20)
		boolean result = ScreenshotDriver.verifyImagesAreDifferent(
				expectedImage,
				actualImage,
				criteriaPercent,
				workdir_.toFile(),
				FailureHandling.OPTIONAL)
	}


	@Ignore
	@Test
	void test_verifyImagesAreDifferent_failingCase() {
		WebUI.comment(">>> test_verifyImagesAreDifferent_failingCase started")
		File expectedImage = Paths.get(RunConfiguration.getProjectDir(),
				'src/test/resources/fixture/images/kazurayam.png').toFile()
		String xpathStr = makeXPathToPhoto(6)
		TestObject actualImage = new TestObject().addProperty('xpath', ConditionType.EQUALS, xpathStr, true)
		Double criteriaPercent = 5.0
		WebUI.navigateToUrl(url_)
		WebUI.verifyElementPresent(actualImage, 20)
		boolean result = ScreenshotDriver.verifyImagesAreDifferent(
				expectedImage,
				actualImage,
				criteriaPercent,
				workdir_.toFile(),
				FailureHandling.OPTIONAL)
	}



	private String makeXPathToPhoto(int index) {
		return "//div[@id='Body']//div[contains(@class,'CommentsWrap')]" +
				"//ul[contains(@class,'Comments')]/li[${index}]" +
				"//div[contains(@class,'CommentHeader')]" +
				"//img[contains(@class,'ProfilePhoto')]"
	}
}
