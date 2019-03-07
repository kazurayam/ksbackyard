package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

@RunWith(JUnit4.class)
public class WebUIExtTest {
	
	@Before
	void setup() {}
	
	@Test
	void test_openWebDriverWithPredefinedUserProfile() {
		WebUIDriverType executedBrowser = DriverFactory.getExecutedBrowser()
		String userProfile = 'Profile 2'
		WebDriver driver = WebUIExt.openWebDriverWithPredefinedUserProfile(executedBrowser, userProfile, FailureHandling.CONTINUE_ON_FAILURE)
		if (driver != null) {
			DriverFactory.changeWebDriver(driver)
			WebUI.closeBrowser()
		}
	}

}
