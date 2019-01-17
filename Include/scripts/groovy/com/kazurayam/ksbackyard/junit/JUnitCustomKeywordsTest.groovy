package com.kazurayam.ksbackyard.junit

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * People want to know which class/which method/which line of JUnit test case caused failure.
 * People want to see that infomation in the Log View.
 * Howerver, the Katalon Studio does not show such detail infomation.
 * I want to improved it. I want to patch Katalon.
 *
 * @author kazurayam
 *
 */
@RunWith(JUnit4.class)
public class JUnitCustomKeywordsTest {

	@Before
	public void setup() {
		WebUI.openBrowser("")
	}

	@After void cleanup() {
		WebUI.closeBrowser()
	}

	@Test
	public void test_findTestObject() {
		WebUI.navigateToUrl("http://demoaut.katalon.com/appointment")
		TestObject to = findTestObject("Page_CURA Healthcare Service/a_Make Appointment")
		WebUI.verifyElementPresent(to, 10)
	}
}
