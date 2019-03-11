package com.kazurayam.ksbackyard.webdriver

import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities

public interface ChromeDesiredCapabilityResolver {

	DesiredCapabilities resolveDesiredCapabilities(ChromeOptions chromeOptions)
	
}
