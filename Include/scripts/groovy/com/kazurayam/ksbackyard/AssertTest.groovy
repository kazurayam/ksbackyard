package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.exception.StepFailedException

@RunWith(JUnit4.class)
class AssertTest {

	@Test
	void test_assertTrue() {
		def expected = "Ken"
		def actual   = "Ken"
		com.kazurayam.ksbackyard.Assert.assertTrue(
				"expected truth that ${expected} is ${actual}, but found false",
				expected == actual,
				FailureHandling.OPTIONAL)
	}
	
	@Test
	void test_assertFalse() {
		def expected = "Ken"
		def actual   = "Merry"
		com.kazurayam.ksbackyard.Assert.assertFalse(
				"$expected truth that {expected} is ${actual}, but found false",
				expected == actual,
				FailureHandling.OPTIONAL)
	}

	@Test
	void test_assertEquals_String() {
		def expected = "Tom"
		def actual   = "Tom"
		com.kazurayam.ksbackyard.Assert.assertEquals(
				"expected(${expected}) was expected to be equal to actual(${actual}), but found not",
				expected, actual,
				FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	@Test
	void test_assertEquals_String_throwsException() {
		try {
			def expected = "Tom"
			def actual   = "Jerry"
			com.kazurayam.ksbackyard.Assert.assertEquals(
				"expected(${expected}) was expected to be equal to actual(${actual}), but found not",
				expected, actual,
				FailureHandling.OPTIONAL)
		} catch (StepFailedException ex) {
			assertEquals("expected(Tom) was expected to be equal to actual(Jerry), but found not", ex.getMessage())
		}
	}

	@Test
	void test_assertEquals_Number() {
		Number expected = 0
		Number actual   = 0
		com.kazurayam.ksbackyard.Assert.assertEquals(
				"expected(${expected}) was expected to be equal to actual(${actual}), but found not",
				expected, actual,
				FailureHandling.CONTINUE_ON_FAILURE)
	}
	
	@Test
	void test_assertEquals_Number_throwsException() {
		try {
			Number expected = 0
			Number actual   = 999
			com.kazurayam.ksbackyard.Assert.assertEquals(
				"expected(${expected}) was expected to be equal to actual(${actual}), but found not",
				expected, actual,
				FailureHandling.OPTIONAL)
		} catch (StepFailedException ex) {
			assertEquals("expected(0) was expected to be equal to actual(999), but found not", ex.getMessage())
		}
	}

}
