
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import org.openqa.selenium.WebDriver

import java.io.File

import ru.yandex.qatools.ashot.comparison.ImageDiff

import java.lang.Float


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
