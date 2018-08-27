package com.kazurayam.ksbackyard.test

import org.junit.Test

import com.kms.katalon.core.model.FailureHandling as FailureHandling

class AssertTest {

	@Test
	void test_assertTrue() {
		def expected = "Ken"
		def actual  = "Marry"
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"${expected} is not ${actual}", expected == actual,
				FailureHandling.OPTIONAL)
	}

	@Test
	void test_assertEquals_String() {
		def expected = "Tom"
		def actual   = "Jerry"
		com.kazurayam.ksbackyard.Assert.assertEquals(
				"${expected} is not equal to ${actual}", expected, actual,
				FailureHandling.CONTINUE_ON_FAILURE)
	}

	@Test(expected = com.kms.katalon.core.exception.StepFailedException.class)
	void test_assertEquals_Number() {
		def expectedNumber = 0
		def actualNumber   = 999
		com.kazurayam.ksbackyard.Assert.assertEquals(
				"${expectedNumber} is not equal to ${actualNumber}", expectedNumber, actualNumber,
				FailureHandling.STOP_ON_FAILURE)
	}
}
