package com.kazurayam.ksbackyard

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test

import com.kms.katalon.core.testobject.TestObject

public class TestObjectSupportTest {

	@Test
	void test_jsonify() {
		TestObject tObj = findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment')
		String json = TestObjectSupport.jsonifyActiveProperties(tObj)
		println json
		assertTrue(json.length() > 0)
		assertTrue("name is missing", json.contains('name'))
		assertTrue("condition is missing", json.contains('condition'))
		assertTrue("value is missing", json.contains('value'))
		assertTrue("isActive is missing", json.contains('isActive'))
	}
}
