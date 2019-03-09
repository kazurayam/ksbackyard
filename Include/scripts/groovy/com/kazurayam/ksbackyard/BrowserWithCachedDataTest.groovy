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

import com.kazurayam.ksbackyard.BrowserWithCachedData.ChromeProfile
import com.kazurayam.ksbackyard.BrowserWithCachedData.ChromeProfileFinder
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * @author urayamakazuaki
 *
 */
@RunWith(JUnit4.class)
public class BrowserWithCachedDataTest {

	@Before
	void setup() {}

	@Ignore
	@Test
	void test_getUserDataDirectory() {
		Path userDataDirectory = BrowserWithCachedData.getUserDataDirectory()
		assertThat(Files.exists(userDataDirectory), is(true))
	}

	@Ignore
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

	@Ignore
	@Test
	void test_ChromeProfileFinder_getChromeProfiles() {
		List<ChromeProfile> chromeProfiles = ChromeProfileFinder.getChromeProfiles()
		assertTrue(chromeProfiles.size() > 0)
	}


	@Test
	@Ignore
	void test_ChromeProfileFinder_listChromeProfiles() {
		String text = ChromeProfileFinder.listChromeProfiles()
		println text
		assertTrue( text.length() > 0)
	}

	@Test
	void test_ChromeProfileFinder_getChromeProfile() {
		ChromeProfile cp = ChromeProfileFinder.getChromeProfile('Katalon')
		assertThat(cp, is(notNullValue()))
		assertThat(cp.getName(), is('Katalon'))
	}

	@Test
	void test_openChromeDriver() {
		ChromeOptions defaultChromeOptions = BrowserWithCachedData.defaultChromeOptions()
		WebDriver driver = BrowserWithCachedData.openChromeDriver('Katalon')
		WebUI.delay(2)
		WebUI.closeBrowser()
	}
}
