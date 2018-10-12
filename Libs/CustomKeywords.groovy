
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import org.openqa.selenium.WebDriver

import org.openqa.selenium.WebElement

import java.io.File

import java.lang.Integer

import java.awt.image.BufferedImage

import java.lang.Double

import java.lang.String

import java.lang.Boolean

import com.kms.katalon.core.model.FailureHandling

import java.lang.Number


def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeElementImage"(
    	WebDriver webDriver	
     , 	WebElement webElement	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeElementImage(
        	webDriver
         , 	webElement)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.saveElementImage"(
    	WebDriver webDriver	
     , 	WebElement webElement	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).saveElementImage(
        	webDriver
         , 	webElement
         , 	file)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePageImage"(
    	WebDriver webDriver	
     , 	Integer timeout	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePageImage(
        	webDriver
         , 	timeout)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage"(
    	WebDriver webDriver	
     , 	File file	
     , 	Integer timeout	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).saveEntirePageImage(
        	webDriver
         , 	file
         , 	timeout)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePage"(
    	WebDriver webDriver	
     , 	File file	
     , 	Integer timeout	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePage(
        	webDriver
         , 	file
         , 	timeout)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyImages"(
    	BufferedImage expectedImage	
     , 	BufferedImage actualImage	
     , 	Double criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyImages(
        	expectedImage
         , 	actualImage
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyVideoInMotion"(
    	WebDriver driver	
     , 	WebElement video	
     , 	WebElement playButton	
     , 	Integer gapTimeSecs	
     , 	Double criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyVideoInMotion(
        	driver
         , 	video
         , 	playButton
         , 	gapTimeSecs
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyVideoStartsStill"(
    	WebDriver driver	
     , 	WebElement video	
     , 	WebElement playButton	
     , 	Integer gapTimeSecs	
     , 	Double criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyVideoStartsStill(
        	driver
         , 	video
         , 	playButton
         , 	gapTimeSecs
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePageImage"(
    	WebDriver webDriver	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePageImage(
        	webDriver)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage"(
    	WebDriver webDriver	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).saveEntirePageImage(
        	webDriver
         , 	file)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePage"(
    	WebDriver webDriver	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePage(
        	webDriver
         , 	file)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyVideoStartsStill"(
    	WebDriver driver	
     , 	WebElement video	
     , 	WebElement playButton	
     , 	Integer gapTimeSecs	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyVideoStartsStill(
        	driver
         , 	video
         , 	playButton
         , 	gapTimeSecs)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyVideoStartsStill"(
    	WebDriver driver	
     , 	WebElement video	
     , 	WebElement playButton	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyVideoStartsStill(
        	driver
         , 	video
         , 	playButton)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.verifyVideoStartsStill"(
    	WebDriver driver	
     , 	WebElement video	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).verifyVideoStartsStill(
        	driver
         , 	video)
}

def static "com.kazurayam.ksbackyard.URLParser.queryParameters"(
    	String urlString	) {
    (new com.kazurayam.ksbackyard.URLParser()).queryParameters(
        	urlString)
}

def static "com.kazurayam.ksbackyard.Assert.assertTrue"(
    	String message	
     , 	Boolean condition	
     , 	FailureHandling flowControl	) {
    (new com.kazurayam.ksbackyard.Assert()).assertTrue(
        	message
         , 	condition
         , 	flowControl)
}

def static "com.kazurayam.ksbackyard.Assert.assertFalse"(
    	String message	
     , 	Boolean condition	
     , 	FailureHandling flowControl	) {
    (new com.kazurayam.ksbackyard.Assert()).assertFalse(
        	message
         , 	condition
         , 	flowControl)
}

def static "com.kazurayam.ksbackyard.Assert.assertEquals"(
    	String message	
     , 	String expected	
     , 	String actual	
     , 	FailureHandling flowControl	) {
    (new com.kazurayam.ksbackyard.Assert()).assertEquals(
        	message
         , 	expected
         , 	actual
         , 	flowControl)
}

def static "com.kazurayam.ksbackyard.Assert.assertEquals"(
    	String message	
     , 	Number expected	
     , 	Number actual	
     , 	FailureHandling flowControl	) {
    (new com.kazurayam.ksbackyard.Assert()).assertEquals(
        	message
         , 	expected
         , 	actual
         , 	flowControl)
}

def static "com.kazurayam.ksbackyard.Assert.assertTrue"(
    	String message	
     , 	Boolean condition	) {
    (new com.kazurayam.ksbackyard.Assert()).assertTrue(
        	message
         , 	condition)
}

def static "com.kazurayam.ksbackyard.Assert.assertFalse"(
    	String message	
     , 	Boolean condition	) {
    (new com.kazurayam.ksbackyard.Assert()).assertFalse(
        	message
         , 	condition)
}

def static "com.kazurayam.ksbackyard.Assert.assertEquals"(
    	String message	
     , 	String expected	
     , 	String actual	) {
    (new com.kazurayam.ksbackyard.Assert()).assertEquals(
        	message
         , 	expected
         , 	actual)
}

def static "com.kazurayam.ksbackyard.Assert.assertEquals"(
    	String message	
     , 	Number expected	
     , 	Number actual	) {
    (new com.kazurayam.ksbackyard.Assert()).assertEquals(
        	message
         , 	expected
         , 	actual)
}

def static "com.kazurayam.ksbackyard.RunConfigurationWrapper.getProjectDir"() {
    (new com.kazurayam.ksbackyard.RunConfigurationWrapper()).getProjectDir()
}

def static "com.kazurayam.ksbackyard.RunConfigurationWrapper.resolveAgainstProjectDir"(
    	String pathString	) {
    (new com.kazurayam.ksbackyard.RunConfigurationWrapper()).resolveAgainstProjectDir(
        	pathString)
}

def static "com.kazurayam.ksbackyard.QuietBrowser.openBrowser"() {
    (new com.kazurayam.ksbackyard.QuietBrowser()).openBrowser()
}

def static "com.kazurayam.ksbackyard.QuietBrowser.getProxyInformation"() {
    (new com.kazurayam.ksbackyard.QuietBrowser()).getProxyInformation()
}

def static "com.kazurayam.ksbackyard.test.WebDriverFactory.createWebDriver"() {
    (new com.kazurayam.ksbackyard.test.WebDriverFactory()).createWebDriver()
}
