package com.kazurayam.ksbackyard.test

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test

import com.kazurayam.ksbackyard.TestObjectSupport

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

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
