package com.kazurayam.ksbackyard.test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.awt.image.BufferedImage

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kazurayam.ksbackyard.ScreenshotDriver
import com.kms.katalon.core.configuration.RunConfiguration

class ScreenshotDriverTest {

	private static WebDriver driver
	private static String url = "https://forum.katalon.com/discussions"
	private static Path workdir

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		driver = WebDriverFactory.createWebDriver()
		workdir = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp')
		Files.createDirectories(workdir)
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
		Thread.sleep(2)
		driver.quit()
	}

	@Ignore
	@Test
	void test_Google() {
		driver.get("https://www.google.co.jp/")
		WebElement element = driver.findElement(By.name("q"))
		element.sendKeys("katalon studio")		//検索文字をセット;
		element.submit()
		assertEquals("Google", driver.getTitle())	//ページタイトルが想定通りか、チェック
	}

	/**
	 * test takeElementImage(WebDriver, WebElement)
	 */
	@Test
	void test_takeElementImage() {
		driver.get("https://forum.katalon.com/discussions")
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div.banner-forum')));
		WebElement banner = driver.findElement(By.cssSelector('div.banner-forum'))
		BufferedImage image = ScreenshotDriver.takeElementImage(driver, banner)
		assertNotNull("image is null", image)
	}
}
