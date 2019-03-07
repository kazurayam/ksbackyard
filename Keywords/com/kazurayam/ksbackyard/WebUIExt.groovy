package com.kazurayam.ksbackyard

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType
import com.kms.katalon.core.configuration.RunConfiguration

public class WebUIExt {

	/**
	 * Based on the post https://forum.katalon.com/t/open-browser-with-custom-profile/19268 by Thanh To
	 * 
	 * Chrome's User Data directory is OS dependent. The User Data Diretory is described in the document
	 * https://chromium.googlesource.com/chromium/src/+/HEAD/docs/user_data_dir.md#Current-Location
	 * 
	 * @param userProfile
	 * @param flowControl
	 * @return
	 */
	@Keyword
	static WebDriver openWebDriverWithPredefinedUserProfile(WebUIDriverType executedBrowser, String userProfile, FailureHandling flowControl) {
		WebDriver webDriver = null
		Path logsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('logs')
		Files.createDirectories(logsDir)
		switch (executedBrowser) {
			case WebUIDriverType.FIREFOX_DRIVER:
				throw new UnsupportedOperationException("firefox is still TODO")
				break
			case WebUIDriverType.IE_DRIVER:
				throw new UnsupportedOperationException("IE is still TODO")
				break
			case WebUIDriverType.CHROME_DRIVER:
				System.setProperty('webdriver.chrome.driver', DriverFactory.getChromeDriverPath())
				System.setProperty('webdriver.chrome.logfile', logsDir.resolve('chromedriver.log').toString())
				Path chromeProfilePath = getChromeProfilePath()
				Path predefinedUserProfile = chromeProfilePath.resolve(userProfile)
				if (Files.exists(predefinedUserProfile)) {
					ChromeOptions chromeOptions = new ChromeOptions()
					chromeOptions.addArguments("user-data-dir=" + chromeProfilePath.toString())
					chromeOptions.addArguments("profile-directory=${userProfile}")
					// The following lines are copy&pasted from 
					// https://stackoverflow.com/questions/50642308/org-openqa-selenium-webdriverexception-unknown-error-devtoolsactiveport-file-d
					chromeOptions.addArguments("start-maximized"); // open Browser in maximized mode
					chromeOptions.addArguments("disable-infobars"); // disabling infobars
					chromeOptions.addArguments("--disable-extensions"); // disabling extensions
					chromeOptions.addArguments("--disable-gpu"); // applicable to windows os only
					chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
					chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
					webDriver = new ChromeDriver(chromeOptions)
				} else {
					Assert.stepFailed("userProfile \"${userProfile}\" is not predefined", flowControl)
				}
				break
			default:
				break
		}
		return webDriver
	}

	/**
	 * It is important that this chromeProfilePath ends with User Data and not with the profile folder
	 * @returns path of the folder where Google Chrome's User Profile is located
	 */
	@Keyword
	static Path getChromeProfilePath() {
		if (OSIdentifier.isWindows()) {
			return Paths.get('C:', 'Users', System.getProperty('user.name'), 'AppData', 'Local', 'Google', 'Chrome', 'User Data')
		} else if (OSIdentifier.isMac()) {
			// ~/Library/Application Support/Google/Chrome
			throw new UnsupportedOperationException("TODO")
		} else if (OSIdentifier.isUnix()) {
			// ~/.config/google-chrome
			throw new UnsupportedOperationException("TODO")
		} else {
			return null
		}
	}
}
