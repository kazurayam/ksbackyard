package com.kazurayam.ksbackyard.webdriver

import org.openqa.selenium.chrome.ChromeOptions

public interface ChromeOptionsResolver {

	ChromeOptions resolveChromeOptions(Map<String, Object> chromePreferences)
	
}
