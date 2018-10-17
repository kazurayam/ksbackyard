package com.kazurayam.ksbackyard.test

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class WebDriverFactory {

	@Keyword
	static WebDriver createWebDriver() {
		String executedBrowser = DriverFactory.getExecutedBrowser().getName()
		WebDriver driver
		switch (executedBrowser) {
			case 'FIREFOX_DRIVER':
				String geckoDriverPath = DriverFactory.getGeckoDriverPath()
				WebUI.comment(">>> geckoDriverPath=${geckoDriverPath}")
				System.setProperty("webdriver.gecko.driver", geckoDriverPath)
			// browser customization with DesiredCapabilities here --- TODO
				driver = new FirefoxDriver()
				break
			case 'CHROME_DRIVER':
				String chromeDriverPath = DriverFactory.getChromeDriverPath()
				WebUI.comment(">>> chromeDriverPath=${chromeDriverPath}")
				System.setProperty("webdriver.chrome.driver", chromeDriverPath)
			// browser customization with DesiredCapabilities here --- TODO
				driver = new ChromeDriver()
				break
			case 'IE_DRIVER':
				String ieDriverPath = DriverFactory.getIEDriverPath()
				WebUI.comment(">>> ieDriverPath=${ieDriverPath}")
				System.setProperty("webdriver.ie.driver", ieDriverPath)
				driver = new InternetExplorerDriver()
				break
			case 'EDGE_DRIVER':
				String edgeDriverPath = DriverFactory.getEdgeDriverPath()
				WebUI.comment(">>> edgeDriverPath=${edgeDriverPath}")
				System.setProperty("webdriver.edge.driver", edgeDriverPath)
			// you can insert code for browser customization here --- TODO
				driver = new EdgeDriver()
				break
			default:
				throw new IllegalStateException("unsupported browser type: ${executedBrowser}")
		}
		return driver
	}
}