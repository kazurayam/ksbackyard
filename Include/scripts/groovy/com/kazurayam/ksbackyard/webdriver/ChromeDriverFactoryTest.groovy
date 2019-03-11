package com.kazurayam.ksbackyard.webdriver

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kazurayam.ksbackyard.webdriver.ChromeProfile
import com.kazurayam.ksbackyard.webdriver.ChromeProfileFinder
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * @author kazurayam
 *
 */
@RunWith(JUnit4.class)
public class ChromeDriverFactoryTest {

	@Before
	void setup() {}

	@Test
	void test_getUserDataDirectory() {
		Path userDataDirectory = ChromeDriverFactory.getChromeUserDataDirectory()
		assertThat(Files.exists(userDataDirectory), is(true))
	}

	@Test
	void test_getChromeDriverPath() {
		Path chromeDriverPath = ChromeDriverFactory.getChromeDriverPath()
		assertThat(chromeDriverPath, is(notNullValue()))
		assertTrue(Files.exists(chromeDriverPath))
	}

	@Test
	void test_myChromeOptions() {
		ChromeDriverFactory cdFactory = new ChromeDriverFactory()
		Map<String, Object> chromePreferences = cdFactory.resolveChromePreferences()
		ChromeOptions cp = cdFactory.resolveChromeOptions(chromePreferences)
		String cpJson = cp.toJsonText()
		//println "#test_defaultChromeOpitons cp=${cpJson}"
		assertTrue(cpJson.length() > 0)
	}

	@Test
	void test_openChromeDriver() {
		ChromeDriverFactory cdFactory = new ChromeDriverFactory()
		WebDriver driver = cdFactory.openChromeDriverWithProfile('Katalon')
		assertThat(driver, is(notNullValue()))
		DriverFactory.changeWebDriver(driver)
		WebUI.navigateToUrl('http://demoaut.katalon.com/')
		WebUI.delay(3)
		WebUI.closeBrowser()
	}
}
