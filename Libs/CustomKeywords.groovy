
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import org.openqa.selenium.WebDriver

import java.io.File

import ru.yandex.qatools.ashot.comparison.ImageDiff

import java.lang.Double

import java.lang.String

import java.lang.Boolean

import com.kms.katalon.core.model.FailureHandling

import java.lang.Number


def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePage"(
    	WebDriver webDriver	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePage(
        	webDriver
         , 	file)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.diffRatioPercent"(
    	ImageDiff diff	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).diffRatioPercent(
        	diff)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.hasSignificantDiff"(
    	ImageDiff diff	
     , 	Double criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).hasSignificantDiff(
        	diff
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs"(
    	String profileExpected	
     , 	String profileActual	
     , 	String tSuiteName	
     , 	Double criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).makeDiffs(
        	profileExpected
         , 	profileActual
         , 	tSuiteName
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs"(
    	String profileExpected	
     , 	String profileActual	
     , 	String tSuiteName	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).makeDiffs(
        	profileExpected
         , 	profileActual
         , 	tSuiteName)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs"(
    	String profileExpected	
     , 	String tSuiteName	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).makeDiffs(
        	profileExpected
         , 	tSuiteName)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs"(
    	String tSuiteName	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).makeDiffs(
        	tSuiteName)
}

def static "com.kazurayam.ksbackyard.URLParser.queryParameters"(
    	String urlString	) {
    (new com.kazurayam.ksbackyard.URLParser()).queryParameters(
        	urlString)
}

def static "com.kazurayam.ksbackyard.QuietBrowser.openBrowser"() {
    (new com.kazurayam.ksbackyard.QuietBrowser()).openBrowser()
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
