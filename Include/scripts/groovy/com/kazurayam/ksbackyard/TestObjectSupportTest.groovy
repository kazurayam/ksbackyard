package com.kazurayam.ksbackyard

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.openqa.selenium.By

import org.junit.Test
import org.junit.Ignore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject

@RunWith(JUnit4.class)
public class TestObjectSupportTest {

	@Test
	void test_toJson() {
		WebUI.comment("test_toJson")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')
		assertNotNull(tObj)
		String json = TestObjectSupport.toJson(tObj)
		//println json
		assertTrue(json.length() > 0)
		assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_BASIC() {
		WebUI.comment("test_prettyPrint_BASIC")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		//println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_XPATH() {
		WebUI.comment("test_prettyPrint_XPATH")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_XPATH')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		//println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_CSS() {
		WebUI.comment("test_prettyPrint_CSS")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_CSS')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		//println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
		//assertTrue("#btn-make-appointment is missing", json.contains('#btn-make-appointment'))
	}


	@Test
	void test_toBy_BASIC() {
		WebUI.comment("test_toBy_BASIC")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')
		//println ">>> " + TestObjectSupport.prettyPrint(tObj)
		By by = TestObjectSupport.toBy(tObj)
		println by
		assertTrue(by.toString().contains("//a[@id = 'btn-make-appointment']"))
	}

	@Test
	void test_toBy_CSS() {
		WebUI.comment("test_toBy_CSS")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_CSS')
		By by = TestObjectSupport.toBy(tObj)
		println by
		assertTrue(by.toString().contains("#btn-make-appointment"))
	}

	@Test
	void test_toBy_XPATH() {
		WebUI.comment("test_toBy_XPATH")
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_XPATH')
		By by = TestObjectSupport.toBy(tObj)
		println by
		assertTrue(by.toString().contains("//a[@id='btn-make-appointment']"))
	}

	@Test
	void test_toBy_List() {
		WebUI.comment("test_toBy_List")
		List<TestObject> toList = new ArrayList<TestObject>()
		toList.add(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC'))
		toList.add(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_CSS'))
		toList.add(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_XPATH'))
		List<By> byList = TestObjectSupport.toBy(toList)
		println byList
		assertEquals(3, byList.size())
	}
}
