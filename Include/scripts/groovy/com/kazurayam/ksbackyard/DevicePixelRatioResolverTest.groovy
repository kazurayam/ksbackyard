package com.kazurayam.ksbackyard

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


@RunWith(JUnit4.class)
public class DevicePixelRatioResolverTest {

	@Test
	public void test_smoke() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('http://demoaut.katalon.com/')
		WebDriver driver = DriverFactory.getWebDriver()
		Float value = DevicePixelRatioResolver.resolveDPR(driver)
		WebUI.closeBrowser()
		assert value == 2.0
	}

}
