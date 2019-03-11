package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.util.regex.Matcher

import java.nio.file.Files
import java.nio.file.Path

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * @author urayamakazuaki
 *
 */
@RunWith(JUnit4.class)
public class ApplicationInfoTest {


	@Test
	@Ignore
	void test_getAppProp() {
		ApplicationInfo appInfo = new ApplicationInfo()
		appInfo.getAppProp().list(System.out)
	}

	@Test
	void test_KATALON_HOME_PATTERN() {
		Matcher	m = ApplicationInfo.KATALON_HOME_PATTERN.matcher('#C:\\Katalon_Studio_Windows_64-5.10.1')
		assertThat(m.matches(), is(true))
		String s = m.group(1)
		println "s=\"${s}\""
		assertThat(s, is('C:\\Katalon_Studio_Windows_64-5.10.1'))
	}

	@Test
	void test_getKatalonHome() {
		ApplicationInfo appInfo = new ApplicationInfo()
		if (OSIdentifier.isWindows()) {
			assertThat(appInfo.getKatalonHome(), containsString('Katalon_Studio_Windows_64'))
		} else if (OSIdentifier.isMac()) {
			assertThat(appInfo.getKatalonHome(), is('/Applications/Google Chrome.app/Contents/MacOS/Google Chrome'))
		} else if (OSIdentifier.isUnix()) {
			assertThat(appInfo.getKatalonHome(), is('/usr/bin/google-chrome'))
		} else {
			throw new IllegalStateException("Windows, Mac, Linux are supported. Other platformas are not supported")
		}
	}
}
