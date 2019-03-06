import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.WebUIDriverType

WebDriver openBrowserWithPredefinedProfile() {
	WebUIDriverType executedBrowser = DriverFactory.getExecutedBrowser()
	switch (executedBrowser) {
		case WebUIDriverType.CHROME_DRIVER:
			System.setProperty('webdriver.chrome.driver', DriverFactory.getChromeDriverPath())
			def osUserName ='qcq0264'
			def chromeProfileName = 'Kazuaki'
			// It is important that this chromeProfilePath ends with User Data and not with the profile folder
			String chromeProfilePath = "C:\\Users\\${osUserName}\\AppData\\Local\\Google\\Chrome\\User Data\\"
			ChromeOptions chromeProfile = new ChromeOptions()
			chromeProfile.addArguments("user-data-dir=" + chromeProfilePath)
			// Here you specify the actual profile folder
			chromeProfile.addArguments("profile-directory=${chromeProfileName}")
			return new ChromeDriver(chromeProfile)
			break
		default:
			return null
	}
}

WebDriver driver = openBrowserWithPredefinedProfile()

if (driver != null) {
	// Here you can continue your test case
	DriverFactory.changeWebDriver(driver)
	WebUI.navigateToUrl("https://www.47news.jp/")
	WebUI.delay(3)
	WebUI.closeBrowser()
} else {
	WebUI.comment("failed to launch browser")
}