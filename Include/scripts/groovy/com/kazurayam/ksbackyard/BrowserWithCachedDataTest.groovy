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

@RunWith(JUnit4.class)
public class BrowserWithCachedDataTest {

	@Before
	void setup() {}

	@Ignore
	@Test
	void test_openChromeDriver_diretory_Default() {
		WebDriver driver = BrowserWithCachedData.openChromeDriver(
								Paths.get('Default'),
								FailureHandling.CONTINUE_ON_FAILURE)
		if (driver != null) {
			WebUI.setViewPortSize(800, 600)
			WebUI.closeBrowser()
		}
	}
	
	@Test
	void test_getChromeProfileDirectoryOf() {
		String user = 'Katalon'
		Path profileDirectory = BrowserWithCachedData.getChromeProfileDirectoryOf(user)
		assertThat(Files.exists(profileDirectory), is(true))
	}

}
