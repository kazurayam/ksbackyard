
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import java.lang.Boolean

import com.kms.katalon.core.testobject.TestObject

import org.openqa.selenium.WebDriver

import java.io.File

import ru.yandex.qatools.ashot.comparison.ImageDiff

import java.lang.Float


def static "com.kazurayam.ksbackyard.Assert.assertTrue"(
    	String message	
     , 	Boolean condition	) {
    (new com.kazurayam.ksbackyard.Assert()).assertTrue(
        	message
         , 	condition)
}

def static "com.kazurayam.ksbackyard.Assert.clickElement"(
    	TestObject to	) {
    (new com.kazurayam.ksbackyard.Assert()).clickElement(
        	to)
}

def static "com.kazurayam.ksbackyard.Assert.getHtmlTableRows"(
    	TestObject table	
     , 	String outerTagName	) {
    (new com.kazurayam.ksbackyard.Assert()).getHtmlTableRows(
        	table
         , 	outerTagName)
}

def static "com.kazurayam.ksbackyard.MaterialsWrapper.getScreenshotPairs"(
    	String expectedProfile	
     , 	String actualProfile	
     , 	String testSuiteId	) {
    (new com.kazurayam.ksbackyard.MaterialsWrapper()).getScreenshotPairs(
        	expectedProfile
         , 	actualProfile
         , 	testSuiteId)
}

def static "com.kazurayam.ksbackyard.MaterialsWrapper.refreshBrowser"() {
    (new com.kazurayam.ksbackyard.MaterialsWrapper()).refreshBrowser()
}

def static "com.kazurayam.ksbackyard.MaterialsWrapper.clickElement"(
    	TestObject to	) {
    (new com.kazurayam.ksbackyard.MaterialsWrapper()).clickElement(
        	to)
}

def static "com.kazurayam.ksbackyard.MaterialsWrapper.getHtmlTableRows"(
    	TestObject table	
     , 	String outerTagName	) {
    (new com.kazurayam.ksbackyard.MaterialsWrapper()).getHtmlTableRows(
        	table
         , 	outerTagName)
}

def static "com.kazurayam.ksbackyard.AShotDriver.takeEntirePage"(
    	WebDriver webDriver	
     , 	File file	) {
    (new com.kazurayam.ksbackyard.AShotDriver()).takeEntirePage(
        	webDriver
         , 	file)
}

def static "com.kazurayam.ksbackyard.AShotDriver.hasSignificantDiff"(
    	ImageDiff diff	
     , 	Float criteriaPercent	) {
    (new com.kazurayam.ksbackyard.AShotDriver()).hasSignificantDiff(
        	diff
         , 	criteriaPercent)
}
