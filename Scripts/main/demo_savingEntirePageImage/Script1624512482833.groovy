// Test Cases/main/demo_savingEntirePageImage

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.openqa.selenium.WebDriver

import com.kazurayam.ksbackyard.DevicePixelRatioResolver
import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kazurayam.ksbackyard.ScreenshotDriver.Options
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path outDir = projectDir.resolve("tmp/main")
Files.createDirectories(outDir)

WebUI.openBrowser("")
WebUI.setViewPortSize(400, 640)
WebUI.navigateToUrl("http://demoaut.katalon.com/")
WebDriver driver = DriverFactory.getWebDriver()

File outFile = outDir.resolve("screenshot.png").toFile()
float dpr = DevicePixelRatioResolver.resolveDPR(driver)
Options options = new Options.Builder()
						.timeout(100)
						.devicePixelRatio(dpr)
						.build()

ScreenshotDriver.saveEntirePageImage(driver, outFile, options)

WebUI.delay(1)
WebUI.closeBrowser()