package com.kazurayam.ksbackyard.test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

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
import com.kazurayam.ksbackyard.ScreenshotDriver.ImageDifference
import com.kms.katalon.core.configuration.RunConfiguration

class ScreenshotDriverTest {

	private static WebDriver driver_
	private static String url_ = "https://forum.katalon.com/discussions"
	private static Path workdir_

	/*
	 * 1. initialize directory where we output PNG files
	 * 2. open browser
	 */
	@BeforeClass
	static void beforeClass() {
		driver_ = WebDriverFactory.createWebDriver()
		workdir_ = Paths.get(RunConfiguration.getProjectDir()).resolve('tmp/ScreenshotDriverTest')
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div.banner-forum')));
		WebElement banner = driver_.findElement(By.cssSelector('div.banner-forum'))
		BufferedImage image = ScreenshotDriver.takeElementImage(driver_, banner)
		assertNotNull("image is null", image)
	}

	/**
	 * test saveElementImage(WebDriver, WebElement, Path) {
	 */
	@Test
	void test_saveElementImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div.banner-forum')));
		WebElement banner = driver_.findElement(By.cssSelector('div.banner-forum'))
		Path output = workdir_.resolve("test_saveElementImage.png")
		ScreenshotDriver.saveElementImage(driver_, banner, output.toFile())
		assertTrue("${output.toString()} does not exist", Files.exists(output))
	}

	/**
	 * test takeEntirePageImage(WebDriver, Integer)
	 */
	@Test
	void test_takeEntirePageImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div.banner-forum')));
		BufferedImage image = ScreenshotDriver.takeEntirePageImage(driver_, 100)
		assertNotNull("image is null", image)
	}

	/**
	 * test saveEntirePageImage(WebDriver, Path, Integer) {
	 */
	@Test
	void test_saveEntirePageImage() {
		driver_.get(url_)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector('div.banner-forum')))
		WebElement banner = driver_.findElement(By.cssSelector('div.banner-forum'))
		Path output = workdir_.resolve("test_saveEntirePageImage.png")
		ScreenshotDriver.saveEntirePageImage(driver_, output.toFile(), 100)
		assertTrue("${output.toString()} does not exist", Files.exists(output))
	}

	/**
	 * test verifyImages(BufferedImage, BufferedImage, Double)
	 */
	@Test
	void test_verifyImages() {
		String expectedUrl = 'https://katalon-demo-cura.herokuapp.com/'
		driver_.get(expectedUrl)
		WebDriverWait wait = new WebDriverWait(driver_, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='btn-make-appointment']")))
		BufferedImage expectedImage = ScreenshotDriver.takeEntirePageImage(driver_)
		//
		String actualUrl = 'http://demoaut-mimic.kazurayam.com'
		driver_.get(actualUrl)
		WebDriverWait wait2 = new WebDriverWait(driver_, 10);
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='btn-make-appointment']")))
		BufferedImage actualImage = ScreenshotDriver.takeEntirePageImage(driver_)
		//
		ImageDifference difference = ScreenshotDriver.verifyImages(expectedImage, actualImage, 3.0)
		//
		assertTrue("${expectedUrl} and ${actualUrl} look similar", difference.imagesAreDifferent())
		// assert ImageDifference properties
		assertTrue("difference returned null expectedImage", difference.getExpectedImage() != null)
		assertTrue("difference returned null actualImage", difference.getActualImage() != null)
		assertTrue("difference returned null diffImage", difference.getDiffImage() != null)
		assertTrue("difference returned criteria ${difference.getCriteria()} != 3.0",
				difference.getCriteria() == 3.0)
		assertTrue("difference returned ratio ${difference.getRatio()} which is unexpected",
				difference.getRatio() > 0 && difference.getRatio() < 10.0)
		assertTrue("difference returned ratioAsString ${difference.getRatioAsString()}",
				difference.getRatioAsString() == '6.05')
		assertTrue("difference returned ${difference.imagesAreDifferent()} for imagesAreDifferent()",
				difference.imagesAreDifferent())
	}
}
