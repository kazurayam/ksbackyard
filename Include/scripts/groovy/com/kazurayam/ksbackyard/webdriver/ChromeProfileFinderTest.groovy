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
public class ChromeProfileFinderTest {

	@Test
	void test_ChromeProfileFinder_getChromeProfiles() {
		List<ChromeProfile> chromeProfiles = ChromeProfileFinder.getChromeProfiles()
		assertTrue(chromeProfiles.size() > 0)
	}

	@Test
	void test_ChromeProfileFinder_listChromeProfiles() {
		String text = ChromeProfileFinder.listChromeProfiles()
		assertTrue( text.length() > 0)
	}

	@Test
	void test_ChromeProfileFinder_getChromeProfile() {
		ChromeProfile cp = ChromeProfileFinder.getChromeProfile('Katalon')
		assertThat(cp, is(notNullValue()))
		assertThat(cp.getName(), is('Katalon'))
	}
}
