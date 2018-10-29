package com.kazurayam.ksbackyard.test

import static org.junit.Assert.assertFalse
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

import com.kazurayam.ksbackyard.ImageDifference
import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class ImageDifferenceTest {

	private static String url_
	private static Path workdir_
	private static File image1_
	private static File image6_

	static {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		Path targetHTML = projectDir.resolve('src/test/resources/fixture/Visual Testing in Katalon Studio - Katalon Q&A.html')
		url_ = targetHTML.toUri().toURL().toExternalForm()
	}

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ImageDifferenceTest')
		if (Files.exists(workdir_)) {
			FileUtils.deleteDirectory(workdir_.toFile())
		}
		Files.createDirectories(workdir_)
		WebUI.openBrowser('')
		image1_ = saveProfilePicture(1, "andrej.png")
		image6_ = saveProfilePicture(6, "kazurayam.png")
		
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
	static private File saveProfilePicture(int index, String filename) {
		String xpathStr = makeXPathToPhoto(index)
		WebUI.navigateToUrl(url_)
		TestObject tObj = new TestObject()
		tObj.addProperty("xpath", ConditionType.EQUALS, xpathStr, true)
		boolean result = WebUI.verifyElementPresent(tObj, 20)
		if (result) {
			//WebUI.scrollToElement(tObj, 10)
			Path output = workdir_.resolve(filename)
			BufferedImage bi = ScreenshotDriver.takeElementImage(tObj)
			ImageIO.write(bi, "PNG", output.toFile())
			assertTrue("${output.toString()} does not exist", Files.exists(output))
			int expectedLength = 20_000
			assertTrue("output.toFile().length()=${output.toFile().length()} is larger than expected ${expectedLength}",
					output.toFile().length() < expectedLength)
			return output.toFile()
		} else {
			fail("Element is not present at ${xpathStr}")
		}
	}

	@Test
	void test_similar() {
		BufferedImage bix = ImageIO.read(image1_)
		BufferedImage biy = ImageIO.read(image1_)
		ImageDifference difference = new ImageDifference(bix, biy)
		difference.setCriteria(15.0)
		assertTrue("expected difference smaller than or equal to 15.0 but was ${difference.getRatioAsString()}", difference.getRatio() <= 15.0)
		assertTrue("expected images are similar but" , difference.imagesAreSimilar())
		assertFalse("expected images are different but" , difference.imagesAreDifferent())
	}

	@Test
	void test_different() {
		BufferedImage bi1 = ImageIO.read(image1_)
		BufferedImage bi6 = ImageIO.read(image6_)
		ImageDifference difference = new ImageDifference(bi1, bi6)
		difference.setCriteria(15.0)
		assertTrue("expected difference greater than 15.0 but was ${difference.getRatioAsString()}", difference.getRatio() > 15.0)
		assertTrue("expected images are different but" , difference.imagesAreDifferent())
		assertFalse("expected images are different but" , difference.imagesAreSimilar())
	}


	static private String makeXPathToPhoto(int index) {
		return "//div[@id='Body']//div[contains(@class,'CommentsWrap')]" +
				"//ul[contains(@class,'Comments')]/li[${index}]" +
				"//div[contains(@class,'CommentHeader')]" +
				"//img[contains(@class,'ProfilePhoto')]"
	}
}
