package com.kazurayam.ksbackyard

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.testobject.TestObject

@RunWith(JUnit4.class)
public class TestObjectSupportTest {

	@Test
	void test_toJson() {
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')
		assertNotNull(tObj)
		String json = TestObjectSupport.toJson(tObj)
		println json
		assertTrue(json.length() > 0)
		assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_BASIC() {
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_BASIC')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_XPATH() {
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_XPATH')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
	}

	@Test
	void test_prettyPrint_CSS() {
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment_CSS')
		assertNotNull(tObj)
		String json = TestObjectSupport.prettyPrint(tObj)
		println json
		assertTrue(json.length() > 0)
		//assertTrue("objectId is missing", json.contains('objectId'))
		//assertTrue("#btn-make-appointment is missing", json.contains('#btn-make-appointment'))
	}

	@Test
	void test_toBy_BASIC() {
		fail()
		
	}

	@Test
	void test_toBy_CSS() {
		fail()
		
	}

	@Test
	void test_toBy_XPATH() {
		fail()
		
	}

	@Test
	void test_toBy_List() {
		fail()
		
	}
}
