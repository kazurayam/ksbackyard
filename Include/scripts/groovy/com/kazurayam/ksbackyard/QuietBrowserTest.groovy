package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@RunWith(JUnit4.class)
class QuietBrowserTest {

	def url = 'http://demoaut.katalon.com/'

	@Test
	void test_openBrowser() {
		def webdriver = QuietBrowser.openBrowser()
		assertNotNull(webdriver)
		assertTrue(webdriver instanceof WebDriver)
		webdriver.get(url)
		Thread.sleep(1000)
		webdriver.quit()
	}

	@Test
	void test_getProxyInformation() {
		def info = QuietBrowser.getProxyInformation()
		WebUI.comment("proxyInformation is:\n${info}")
		assertThat(info, is(not(nullValue())))
	}
}