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
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kazurayam.ksbackyard.ScreenshotDriver.Options
import com.kazurayam.webdriverfactory4ks.ChromeDriverFactory
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

/**
 * Test ScreeshotDriver class, especially for saveEntirePage() method taking argument of type
 * ScreenshotDriverOptions with TestObjects to ignore WebElements when capturing
 *
 * @author
 *
 */
@RunWith(JUnit4.class)
class ScreenshotDriverResizingTest {

	private static String url_
	private static Path fixturedir_
	private static Path workdir_

	@BeforeClass
	static void beforeClass() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		fixturedir_ = projectDir.resolve('Include/resources/fixture')
		url_ = 'https://www.47news.jp/'
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverResizingTest')
		if (Files.exists(workdir_)) {
			FileUtils.deleteQuietly(workdir_.toFile())
		}
		Files.createDirectories(workdir_)
		//WebUI.openBrowser('')
		ChromeDriverFactory cdFactory = new ChromeDriverFactory()
		WebDriver driver = cdFactory.openChromeDriverWithProfile('Katalon')
		DriverFactory.changeWebDriver(driver)
		WebUI.setViewPortSize(1280, 800)
		//
		ScreenshotDriver.setForceSnapshots(true)
	}

	@Before
	void before() {}

	@After
	void after() {}

	@AfterClass
	static void afterClass() {
		WebUI.closeBrowser()
	}

	@Test
	void test_resize_47News() {
		WebUI.comment(">>> test_resize_47News")
		WebUI.navigateToUrl(url_)

		Path output1280 = workdir_.resolve("47News_top.1280xH.png")
		Path output640  = workdir_.resolve("47News_top.640xH.png")

		ScreenshotDriver.saveEntirePageImage(output1280.toFile())

		Options options = new Options.Builder().width(640).build()
		ScreenshotDriver.saveEntirePageImage(output640.toFile(), options)

		assertTrue("${output640.getFileName()} was expected to be much smaller than ${output1280.getFileName()}",
				output640.toFile().length() < output1280.toFile().length() / 2)
	}
}