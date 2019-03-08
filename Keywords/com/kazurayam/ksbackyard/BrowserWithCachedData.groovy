package com.kazurayam.ksbackyard

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory

public class BrowserWithCachedData {

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
	static WebDriver openChromeDriver(Path profileDirectoryName, FailureHandling flowControl) {
		WebDriver webDriver = null

		Path logsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp')
		Files.createDirectories(logsDir)
		System.setProperty('webdriver.chrome.logfile', logsDir.resolve('chromedriver.log').toString())
		//
		Path chromeDriverPath = BrowserWithCachedData.getChromeDriverPath()
		System.setProperty('webdriver.chrome.driver', chromeDriverPath.toString())
		//
		Path chromeProfilePath = BrowserWithCachedData.getChromeProfilePath()
		Path profileDirectoryPath = chromeProfilePath.resolve(profileDirectoryName)
		if (Files.exists(profileDirectoryPath) && profileDirectoryPath.toFile().canWrite()) {
			ChromeOptions chromeOptions = new ChromeOptions()
			chromeOptions.addArguments("user-data-dir=" + chromeProfilePath.toString())
			chromeOptions.addArguments("profile-directory=${profileDirectoryName}")

			// The following lines are copy&pasted from
			// https://stackoverflow.com/questions/50642308/org-openqa-selenium-webdriverexception-unknown-error-devtoolsactiveport-file-d
			chromeOptions.addArguments("start-maximized")           // open Browser in maximized mode
			chromeOptions.addArguments("disable-infobars")          // disabling infobars
			//chromeOptions.addArguments("--disable-extensions")      // disabling extensions
			chromeOptions.addArguments("--disable-gpu")             // applicable to windows os only
			chromeOptions.addArguments("--disable-dev-shm-usage")   // overcome limited resource problems
			chromeOptions.addArguments("--no-sandbox")              // Bypass OS security model
			WebDriver driver = new ChromeDriver(chromeOptions)
			DriverFactory.changeWebDriver(driver)
			return driver

		} else {
			Assert.stepFailed("Profile directory \"${profileDirectoryPath.toString()}\" is not present", flowControl)
		}
		return webDriver
	}


	/**
	 * https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver#requirements
	 */
	@Keyword
	static Path getChromeDriverPath() {
		if (OSIdentifier.isWindows()) {
			// "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
			return Paths.get("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe")
		} else if (OSIdentifier.isMac()) {
			return Paths.get('/Applications/Google Chrome.app/Contents/MacOS/Google Chrome')
		} else if (OSIdentifier.isUnix()) {
			return Paths.get('/usr/bin/google-chrome')
		} else {
			throw new IllegalStateException(
			"Windows, Mac, Linux are supported. Other platforms are not supported")
		}
	}

	/**
	 * It is important that this chromeProfilePath ends with User Data and not with the profile folder
	 * @returns path of the folder where Google Chrome's User Profile is located
	 */
	@Keyword
	static Path getChromeProfilePath() {
		if (OSIdentifier.isWindows()) {
			// %HOME%\AppData\Local\Google\Chrome\User Data
			return Paths.get('C:', 'Users', System.getProperty('user.name'),
					'AppData', 'Local', 'Google', 'Chrome', 'User Data')
		} else if (OSIdentifier.isMac()) {
			// ~/Library/Application Support/Google/Chrome
			return Paths.get(System.getProperty('user.home')).resolve('Library').
					resolve('Application Support').resolve('Google').resolve('Chrome')
		} else if (OSIdentifier.isUnix()) {
			// ~/.config/google-chrome
			return Paths.get(System.getProperty('user.home')).resolve('.config').
					resolve('google-chrome')
		} else {
			throw new IllegalStateException(
			"Windows, Mac, Linux are supported. Other platforms are not supported.")
		}
	}
	
	@Keyword
	static Path getChromeProfileDirectoryOf(String userName) {
		Path userDataDir = BrowserWithCachedData.getChromeProfilePath()
		// use lambda and stream to filter Preferences files
		throw new UnsupportedOperationException("TODO")
	}
}
