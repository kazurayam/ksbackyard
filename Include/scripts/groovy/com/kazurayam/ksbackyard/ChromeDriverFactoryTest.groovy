package com.kazurayam.ksbackyard

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

import com.kazurayam.ksbackyard.ChromeDriverFactory.ChromeProfile
import com.kazurayam.ksbackyard.ChromeDriverFactory.ChromeProfileFinder
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * @author urayamakazuaki
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
	void test_ChromeProfile() {
		// when:
		Path defaultProfileDirectory = ChromeDriverFactory.getChromeUserDataDirectory().resolve('Default')
		ChromeProfile defaultProfile = new ChromeProfile(defaultProfileDirectory)
		// then:
		assertThat(defaultProfile, is(notNullValue()))
		assertThat(defaultProfile.getName(), is(notNullValue()))
		assertThat(defaultProfile.getPreferences(), is(notNullValue()))
		assertThat(defaultProfile.getProfilePath(), is(defaultProfileDirectory))
	}

	@Test
	void test_ChromeProfileFinder_getChromeProfiles() {
		List<ChromeProfile> chromeProfiles = ChromeProfileFinder.getChromeProfiles()
		assertTrue(chromeProfiles.size() > 0)
	}


	@Test
	@Ignore
	void test_ChromeProfileFinder_listChromeProfiles() {
		String text = ChromeProfileFinder.listChromeProfiles()
		assertTrue( text.length() > 0)
	}

	@Test
	void test_myChromeOptions() {
		ChromeOptions cp = ChromeDriverFactory.myChromeOptions()
		String cpJson = cp.toJsonText()
		//println "#test_defaultChromeOpitons cp=${cpJson}"
		assertTrue(cpJson.length() > 0)
	}

	@Test
	void test_ChromeProfileFinder_getChromeProfile() {
		ChromeProfile cp = ChromeProfileFinder.getChromeProfile('Katalon')
		assertThat(cp, is(notNullValue()))
		assertThat(cp.getName(), is('Katalon'))
	}

	@Test
	void test_getChromeDriverPath() {
		Path chromeDriverPath = ChromeDriverFactory.getChromeDriverPath()
		assertThat(chromeDriverPath, is(notNullValue()))
		assertTrue(Files.exists(chromeDriverPath))	
	}
	
	@Test
	void test_openChromeDriver() {
		ChromeOptions defaultChromeOptions = ChromeDriverFactory.myChromeOptions()
		WebDriver driver = ChromeDriverFactory.openChromeDriver('Katalon')
		assertThat(driver, is(notNullValue()))
		DriverFactory.changeWebDriver(driver)
		WebUI.navigateToUrl('http://demoaut.katalon.com/')
		WebUI.delay(3)
		WebUI.closeBrowser()
	}
}
