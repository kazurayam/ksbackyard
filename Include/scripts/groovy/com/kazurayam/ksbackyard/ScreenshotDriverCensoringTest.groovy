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
class ScreenshotDriverCensoringTest {

	private static String url_
	private static Path fixturedir_
	private static Path workdir_

	@BeforeClass
	static void beforeClass() {
		Path projectDir = Paths.get(RunConfiguration.getProjectDir())
		fixturedir_ = projectDir.resolve('Include/resources/fixture')
		url_ = 'https://www.47news.jp/'
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverCencoringTest')
		if (Files.exists(workdir_)) {
			FileUtils.deleteQuietly(workdir_.toFile())
		}
		Files.createDirectories(workdir_)
		//WebUI.openBrowser('')
		ChromeDriverFactory cdFactory = new ChromeDriverFactory()
		WebDriver driver = cdFactory.openChromeDriver()
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
	void test_censor_47News() {
		WebUI.comment(">>> test_cencor_47News")
		WebUI.navigateToUrl(url_)

		Path output = workdir_.resolve("47News_top.png")
		TestObject mainBnr = findTestObject('Object Repository/Page_47News/div_main-bnr')
		TestObject sidebar = findTestObject('Object Repository/Page_47News/div_sidebar')

		boolean mainBnrPresent = WebUI.verifyElementPresent(mainBnr, 10, FailureHandling.OPTIONAL)
		boolean sidebarPresent = WebUI.verifyElementPresent(sidebar, 10, FailureHandling.OPTIONAL)
		if (mainBnrPresent) {
			if (sidebarPresent) {
				WebUI.delay(1)
				Options options = new Options.Builder().
						timeout(300).
						addIgnoredElement(mainBnr).
						addIgnoredElement(sidebar).
						build()

				ScreenshotDriver.saveEntirePageImage(output.toFile(), options)

				assertTrue("failed to create ${output.toString()}", Files.exists(output))
				int minimumLength = 100_000
				assertTrue("output.toFile().length=${output.toFile().length()} is less than minimum",
						output.toFile().length() > minimumLength)
			} else {
				fail("${sidebar} is not present in ${url_}")
			}
		} else {
			fail("${mainBnr} is not present in ${url_}")
		}
	}
}