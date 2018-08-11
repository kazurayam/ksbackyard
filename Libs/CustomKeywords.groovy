
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import org.openqa.selenium.WebDriver

import java.io.File

import ru.yandex.qatools.ashot.comparison.ImageDiff

import java.lang.Float

import java.lang.String

import java.lang.Boolean


def static "com.kazurayam.ksbackyard.ScreenshotDriver.takeEntirePage"(
    	WebDriver webDriver	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).takeEntirePage(
        	webDriver
         , 	file)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.hasSignificantDiff"(
    	ImageDiff diff	
     , 	Float criteriaPercent	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).hasSignificantDiff(
        	diff
         , 	criteriaPercent)
}

def static "com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs"(
    	String profileExpected	
     , 	String profileActual	
     , 	String tSuiteName	
     , 	Float criteria	) {
    (new com.kazurayam.ksbackyard.ScreenshotDriver()).makeDiffs(
        	profileExpected
         , 	profileActual
         , 	tSuiteName
         , 	criteria)
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

def static "com.kazurayam.ksbackyard.Assert.assertTrue"(
    	String message	
     , 	Boolean condition	) {
    (new com.kazurayam.ksbackyard.Assert()).assertTrue(
        	message
         , 	condition)
}
