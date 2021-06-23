package com.kazurayam.ksbackyard

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.apache.commons.io.FileUtils

@RunWith(JUnit4.class)
class ScreenshotDriverTest {

	private static WebDriver driver_
	private static String url_ = "https://forum.katalon.com"
	private static Path workdir_

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		driver_ = WebDriverFactory.createWebDriver()
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverTest')
		if (Files.exists(workdir_)) {
			FileUtils.deleteDirectory(workdir_.toFile())
		}
		Files.createDirectories(workdir_)
	}

	@Before
	void before() {
	}

	@After
	void after() {}

	/*
	 * close browser
	 */
	@AfterClass
	static void afterClass() {
		Thread.sleep(1000)
		driver_.quit()
	}

	@Ignore
	@Test
	void test_Google() {
		driver_.get("https://www.google.co.jp/")
		WebElement element = driver_.findElement(By.name("q"))
		element.sendKeys("katalon studio")		//検索文字をセット;
		element.submit()
		assertEquals("Google", driver_.getTitle())	//ページタイトルが想定通りか、チェック
	}

	/**
	 * test takeElementImage(WebDriver, WebElement)
	 */
	@Test
	void test_takeElementImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('img#site-logo')));
		WebElement logo = driver_.findElement(By.cssSelector('img#site-logo'))
		BufferedImage image = ScreenshotDriver.takeElementImage(driver_, logo)
		assertNotNull("image is null", image)
	}

	/**
	 * test saveElementImage(WebDriver, WebElement, Path) {
	 */
	@Test
	void test_saveElementImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('img#site-logo')));
		WebElement logo = driver_.findElement(By.cssSelector('img#site-logo'))
		Path output = workdir_.resolve("test_saveElementImage.png")
		ScreenshotDriver.saveElementImage(driver_, logo, output.toFile())
		assertTrue("${output.toString()} does not exist", Files.exists(output))
	}

	/**
	 * test takeEntirePageImage(WebDriver, Integer)
	 */
	@Test
	void test_takeEntirePageImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('img#site-logo')));
		ScreenshotDriver.Options options = new ScreenshotDriver.Options.Builder().timeout(100).devicePixelRatio(2.0f).build()
		BufferedImage image = ScreenshotDriver.takeEntirePageImage(driver_, options)
		assertNotNull("image is null", image)
	}

	/**
	 * test saveEntirePageImage(WebDriver, Path, Integer) {
	 */
	@Test
	void test_saveEntirePageImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('img#site-logo')))
		WebElement logo = driver_.findElement(By.cssSelector('img#site-logo'))
		Path output = workdir_.resolve("test_saveEntirePageImage.png")
		ScreenshotDriver.Options options = new ScreenshotDriver.Options.Builder().timeout(100).devicePixelRatio(2.0f).build()
		ScreenshotDriver.saveEntirePageImage(driver_, output.toFile(), options)
		assertTrue("${output.toString()} does not exist", Files.exists(output))
	}



	static class WebDriverFactory {
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
}
