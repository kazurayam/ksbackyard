package com.kazurayam.ksbackyard

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.testobject.TestObject

@RunWith(JUnit4.class)
public class ScreenshotDriverOptionsTest {

	@Test
	void test_toString() {
		ScreenshotDriverOptions options = new ScreenshotDriverOptions.Builder().
				timeout(100).
				build()
		String s = options.toString()
		println s
		assertTrue("s should contain 100", s.contains('100'))
	}

	@Test
	void test_getTimeout_default() {
		ScreenshotDriverOptions options = new ScreenshotDriverOptions.Builder().build()
		int timeout = options.getTimeout()
		assertEquals(300, timeout)
	}

	@Test
	void test_setTimeout() {
		ScreenshotDriverOptions options = new ScreenshotDriverOptions.Builder().timeout(100).build()
		int timeout = options.getTimeout()
		assertEquals(100, timeout)
	}


	@Test
	void test_addIgnoredElement() {
		ScreenshotDriverOptions options = new ScreenshotDriverOptions.Builder().
				addIgnoredElement(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')).
				addIgnoredElement(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_CSS')).
				addIgnoredElement(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_XPATH')).
				build()
		assertEquals(3, options.getIgnoredElements().size())
	}
}
