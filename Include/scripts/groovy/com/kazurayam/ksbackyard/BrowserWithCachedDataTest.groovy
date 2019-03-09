package com.kazurayam.ksbackyard

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kazurayam.ksbackyard.BrowserWithCachedData
import com.kazurayam.ksbackyard.BrowserWithCachedData.ChromeProfile
import com.kazurayam.ksbackyard.BrowserWithCachedData.ChromeProfileFinder

@RunWith(JUnit4.class)
public class BrowserWithCachedDataTest {

	@Before
	void setup() {}

	@Test
	void test_getUserDataDirectory() {
		Path userDataDirectory = BrowserWithCachedData.getUserDataDirectory()
		assertThat(Files.exists(userDataDirectory), is(true))
	}

	@Test
	void test_ChromeProfile() {
		// when:
		Path defaultProfileDirectory = BrowserWithCachedData.getUserDataDirectory().resolve('Default')
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
	void test_ChromeProfileFinder_listChromeProfiles() {
		String text = ChromeProfileFinder.listChromeProfiles()
		println text
		assertTrue( text.length() > 0)
	}

	@Test
	void test_ChromeProfileFinder_getChromeProfile() {
		String userName = 'Katalon'
		ChromeProfile cp = ChromeProfileFinder.getChromeProfile(userName)
		assertThat(cp, is(notNullValue()))
	}
	
	@Test
	void test_openChromeDriver() {
		WebDriver driver = BrowserWithCachedData.openChromeDriver('Katalon', FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.delay(2)
		WebUI.closeBrowser()
	}
}
