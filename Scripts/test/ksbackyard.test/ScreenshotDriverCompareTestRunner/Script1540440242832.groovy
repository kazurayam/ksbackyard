import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import org.junit.runner.JUnitCore
import org.junit.runner.Result

import com.kazurayam.ksbackyard.test.ScreenshotDriverCompareTest
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

// force compile Keywords
WebUI.callTestCase(findTestCase('test/Prologue'), [:],
	FailureHandling.CONTINUE_ON_FAILURE)

// run CalculatorTest
Result result = JUnitCore.runClasses(ScreenshotDriverCompareTest.class)

// print test result
WebUI.callTestCase(findTestCase('test/Epilogue'), ['result':result],
	FailureHandling.CONTINUE_ON_FAILURE)