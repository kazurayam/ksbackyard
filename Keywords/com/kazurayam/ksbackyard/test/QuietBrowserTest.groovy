package com.kazurayam.ksbackyard.test

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test

import com.kazurayam.ksbackyard.QuietBrowser
import org.openqa.selenium.WebDriver

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

}