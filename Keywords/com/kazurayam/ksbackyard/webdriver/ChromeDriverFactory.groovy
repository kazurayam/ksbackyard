package com.kazurayam.ksbackyard.webdriver

import com.kazurayam.ksbackyard.ApplicationInfo
import com.kazurayam.ksbackyard.Assert
import com.kazurayam.ksbackyard.OSIdentifier

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

public class ChromeDriverFactory {

	static Logger logger_ = LoggerFactory.getLogger(ChromeDriverFactory.class)

	static {
		// wea add toJsonText method to ChromeOptions class
		ChromeOptions.metaClass.toJsonText = {
			return JsonOutput.prettyPrint(JsonOutput.toJson(delegate.asMap()))
		}
	}

	ChromeDriverFactory() {}

	@Keyword
	static WebDriver openChromeDriver(String userName) {
		ChromeOptions myChromeOptions = ChromeDriverFactory.myChromeOptions()
		FailureHandling flowControl = RunConfiguration.getDefaultFailureHandling()
		return openChromeDriver(userName, myChromeOptions, flowControl)
	}

	@Keyword
	static WebDriver openChromeDriver(String userName, ChromeOptions chromeOptions) {
		FailureHandling flowControl = RunConfiguration.getDefaultFailureHandling()
		return openChromeDriver(userName, chromeOptions, flowControl)
	}

	@Keyword
	static WebDriver openChromeDriver(String userName, FailureHandling flowControl) {
		ChromeOptions myChromeOptions = ChromeDriverFactory.myChromeOptions()
		return openChromeDriver(userName, myChromeOptions, flowControl)
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
			ChromeOptions chromeOptions,
			FailureHandling flowControl) {
		Objects.requireNonNull(userName, "userName must not be null")
		Objects.requireNonNull(chromeOptions, "chromeOptions must not be null")
		Objects.requireNonNull(flowControl, "flowControl must not be null")
		//
		Path logsDir = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp')
		Files.createDirectories(logsDir)
		Path chromeDriverLog = logsDir.resolve('chromedriver.log')
		System.setProperty('webdriver.chrome.logfile', chromeDriverLog.toString())
		System.setProperty("webdriver.chrome.verboseLogging", "true")
		//
		Path chromeDriverPath = ChromeDriverFactory.getChromeDriverPath()
		System.setProperty('webdriver.chrome.driver', chromeDriverPath.toString())
		//
		Path userDataDirectory = ChromeDriverFactory.getChromeUserDataDirectory()
		//
		Path profileDirectory = ChromeDriverFactory.getChromeProfileDirectory(userName)
		//
		if (profileDirectory != null) {
			if (Files.exists(profileDirectory) && profileDirectory.toFile().canWrite()) {
				// use the Profile as specified
				chromeOptions.addArguments("user-data-dir=" + userDataDirectory.toString())
				chromeOptions.addArguments("profile-directory=${profileDirectory.getFileName().toString()}")
				KeywordUtil.logInfo("#openChromeDriver chromeOptions=" + chromeOptions.toJsonText())
				DesiredCapabilities cap = myChromeDesiredCapabilities(chromeOptions)
				WebDriver driver = new ChromeDriver(cap)
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
	 * https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver#requirements
	 */
	@Keyword
	static Path getChromeBinaryPath() {
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

	@Keyword
	static Path getChromeDriverPath() {
		ApplicationInfo appInfo = new ApplicationInfo()
		String katalonHome = appInfo.getKatalonHome()
		if (OSIdentifier.isWindows()) {
			return Paths.get(katalonHome).resolve('configuration').
					resolve('resources').resolve('drivers').
					resolve('chromedriver_win32').resolve('chromedriver.exe')
		} else if (OSIdentifier.isMac()) {
			return Paths.get(katalonHome).resolve('Contents').
					resolve('Eclipse').resolve('Configuration').
					resolve('resources').resolve('drivers').
					resolve('chromedriver_mac').resolve('chromedriver')
		} else if (OSIdentifier.isUnix()) {
			throw new UnsupportedOperationException("TODO")
		} else {
			throw new IllegalStateException(
			"Windows, Mac, Linux are supported. Other platforms are not supported.")
		}
	}

	/**
	 * as described https://chromium.googlesource.com/chromium/src/+/HEAD/docs/user_data_dir.md
	 *
	 * @returns path of the directory in which Google Chrome's Profile directories are located
	 */
	@Keyword
	static Path getChromeUserDataDirectory() {
		return ChromeProfileFinder.getChromeUserDataDirectory()
	}

	@Keyword
	static Path getChromeProfileDirectory(String name) {
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
	 * @return
	 */
	static Map<String, Object> myChromePreferences() {
		Map<String, Object> chromePreferences = new HashMap<>()
		// Below two preference settings will disable popup dialog when download file
		chromePreferences.put('profile.default_content_settings.popups', 0)
		chromePreferences.put('download.prompt_for_download', false)
		// set directory to save files
		Path downloads = Paths.get(System.getProperty('user.home'), 'Downloads')
		chromePreferences.put('download.default_directory', downloads.toString())
		// disable flash and pdf viewer
		chromePreferences.put('plugins.plugins_disabled',
				['Adobe Flash Player', 'Chrome PDF Viewer'])
		return chromePreferences
	}

	/**
	 *
	 */
	static ChromeOptions myChromeOptions() {
		ChromeOptions options = new ChromeOptions()
		// set location of the Chrome Browser's binary
		options.setBinary(ChromeDriverFactory.getChromeBinaryPath().toString());
		// set my chrome preferences
		options.setExperimentalOption('prefs', myChromePreferences())
		// The following lines are copy&pasted from
		// https://github.com/SeleniumHQ/selenium/issues/4961
		options.addArguments("--headless")     // necessary for working around the "(unknown error: DevToolsActivePort file doesn't exist)"
		options.addArguments("window-size=1024,768")
		options.addArguments("--no-sandbox")

		//options.addArguments("--single-process")
		options.addArguments("disable-infobars")        // disabling infobars
		//chromeOptions.addArguments("disable-extensions")    // disabling extensions
		options.addArguments("disable-gpu")             // applicable to windows os only
		options.addArguments("disable-dev-shm-usage")   // overcome limited resource problems
		//
		return options
	}

	/**
	 *
	 */
	static DesiredCapabilities myChromeDesiredCapabilities(ChromeOptions chromeOptions) {
		DesiredCapabilities cap = DesiredCapabilities.chrome()
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions)
		return cap
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
			Path userDataDirectory = ChromeDriverFactory.getChromeUserDataDirectory()
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
		static Path getChromeUserDataDirectory() {
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
