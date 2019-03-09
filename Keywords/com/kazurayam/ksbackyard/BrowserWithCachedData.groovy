package com.kazurayam.ksbackyard

import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory

import groovy.json.JsonSlurper

public class BrowserWithCachedData {

	@Keyword
	static WebDriver openChromeDriver(String userName) {
		ChromeOptions defaultChromeOptions = BrowserWithCachedData.defaultChromeOptions()
		FailureHandling flowControl = RunConfiguration.getDefaultFailureHandling()
		return openChromeDriver(userName, defaultChromeOptions, flowControl)
	}

	@Keyword
	static WebDriver openChromeDriver(String userName, ChromeOptions defaultChromeOptions) {
		FailureHandling flowControl = RunConfiguration.getDefaultFailureHandling()
		return openChromeDriver(userName, defaultChromeOptions, flowControl)
	}

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
	static WebDriver openChromeDriver(
			String userName,
			ChromeOptions defaultChromeOptions,
			FailureHandling flowControl) {
		Objects.requireNonNull(userName, "userName must not be null")
		Objects.requireNonNull(defaultChromeOptions, "defaultChromeOptions must not be null")
		Objects.requireNonNull(flowControl, "flowControl must not be null")
		//
		Path logsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp')
		Files.createDirectories(logsDir)
		System.setProperty('webdriver.chrome.logfile', logsDir.resolve('chromedriver.log').toString())
		//
		Path chromeDriverPath = BrowserWithCachedData.getChromeDriverPath()
		System.setProperty('webdriver.chrome.driver', chromeDriverPath.toString())
		//
		Path userDataDirectory = BrowserWithCachedData.getUserDataDirectory()
		//
		Path profileDirectory = BrowserWithCachedData.getProfileDirectory(userName)
		//
		if (profileDirectory != null) {
			if (Files.exists(profileDirectory) && profileDirectory.toFile().canWrite()) {
				ChromeOptions chromeOptions = defaultChromeOptions
				chromeOptions.addArguments("user-data-dir=" + userDataDirectory.toString())
				chromeOptions.addArguments("profile-directory=${profileDirectory.getFileName().toString()}")
				WebDriver driver = new ChromeDriver(chromeOptions)
				DriverFactory.changeWebDriver(driver)
				return driver
			} else {
				Assert.stepFailed("Profile directory \"${profileDirectory.toString()}\" is not present", flowControl)
			}
		} else {
			Assert.stepFailed("Profile directory for userName \"${userName}\" is not found " +
					"under ${userDataDirectory.toString()}.\n" + ChromeProfileFinder.listChromeProfiles()
					)
		}
	}

	/**
	 * 
	 */
	static ChromeOptions defaultChromeOptions() {
		ChromeOptions chromeOptions = new ChromeOptions()
		// The following lines are copy&pasted from
		// https://stackoverflow.com/questions/50642308/org-openqa-selenium-webdriverexception-unknown-error-devtoolsactiveport-file-d
		chromeOptions.addArguments("start-maximized")           // open Browser in maximized mode
		chromeOptions.addArguments("disable-infobars")          // disabling infobars
		//chromeOptions.addArguments("--disable-extensions")      // disabling extensions
		chromeOptions.addArguments("--disable-gpu")             // applicable to windows os only
		chromeOptions.addArguments("--disable-dev-shm-usage")   // overcome limited resource problems
		chromeOptions.addArguments("--no-sandbox")              // Bypass OS security model
		// https://github.com/SeleniumHQ/selenium/issues/4961
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
	 * as described https://chromium.googlesource.com/chromium/src/+/HEAD/docs/user_data_dir.md
	 * 
	 * @returns path of the directory in which Google Chrome's Profile directories are located
	 */
	@Keyword
	static Path getUserDataDirectory() {
		return ChromeProfileFinder.getUserDataDirectory()
	}

	@Keyword
	static Path getProfileDirectory(String name) {
		Objects.requireNonNull(name, "name must not be null")
		ChromeProfile cProfile = ChromeProfileFinder.getChromeProfile(name)
		if (cProfile != null) {
			return cProfile.getProfilePath()
		} else {
			return null
		}
	}

	/**
	 * 
	 * @author kazurayam
	 *
	 */
	static final class ChromeProfileFinder {
		private ChromeProfileFinder() {}
		/*
		 * 
		 */
		static List<ChromeProfile> getChromeProfiles() {
			List<ChromeProfile> chromeProfiles = new ArrayList<ChromeProfile>()
			Path userDataDirectory = BrowserWithCachedData.getUserDataDirectory()
			List<Path> dirStream = Files.list(userDataDirectory).collect(Collectors.toList());
			for (Path dir : dirStream) {
				if (Files.exists(dir.resolve('Preferences'))) {
					ChromeProfile cp = new ChromeProfile(dir)
					chromeProfiles.add(cp)
				}
			}
			return chromeProfiles
		}

		static String listChromeProfiles() {
			List<ChromeProfile> chromeProfiles = getChromeProfiles()
			Collections.sort(chromeProfiles)
			StringBuilder sb = new StringBuilder()
			sb.append(String.format("%-15s","user name"))
			sb.append("\t|")
			sb.append("profile path")
			sb.append("\n")
			sb.append(('-' * 15) + '\t|' + ('-' * 15) + "\n")
			for (ChromeProfile cp : chromeProfiles) {
				sb.append(String.format("%-15s",cp.getName()))
				sb.append("\t|")
				sb.append(cp.getProfilePath().getFileName().toString())
				sb.append("\n")
			}
			return sb.toString()
		}

		/**
		 * 
		 * @param name
		 * @return
		 */
		static ChromeProfile getChromeProfile(String name) {
			List<ChromeProfile> chromeProfiles = this.getChromeProfiles()
			for (ChromeProfile cProfile: chromeProfiles) {
				if (cProfile.getName().equals(name)) {
					return cProfile
				}
			}
			return null
		}

		/**
		 * 
		 * @return
		 */
		static Path getUserDataDirectory() {
			if (OSIdentifier.isWindows()) {
				// It is important that this chromeProfilesPath ends with User Data and not with the profile folder
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
	}

	/**
	 * 
	 * @author kazuraayam
	 *
	 */
	static final class ChromeProfile implements Comparable<ChromeProfile> {
		static final String PREFERENCES_FILE_NAME = 'Preferences'
		private Path profilePath_ = null
		private def preferences_ = null
		private String name_ = null
		ChromeProfile(Path profilePath) {
			Path preferences = profilePath.resolve(PREFERENCES_FILE_NAME)
			if (!Files.exists(preferences)) {
				throw new IOException("${preferences.toString()} is not found")
			}
			this.profilePath_ = profilePath
			JsonSlurper slurper = new JsonSlurper()
			this.preferences_ = slurper.parse(preferences.toFile())
			this.name_ = this.preferences_['profile']['name']
		}
		String getName() {
			return this.name_
		}
		def getPreferences() {
			return this.preferences_
		}
		Path getProfilePath() {
			return this.profilePath_
		}
		@Override
		int compareTo(ChromeProfile other) {
			return this.getName().compareTo(other.getName())
		}
	}
}
