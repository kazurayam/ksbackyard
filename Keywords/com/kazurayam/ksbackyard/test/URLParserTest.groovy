package com.kazurayam.ksbackyard.test

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test

import com.kazurayam.ksbackyard.URLParser

class URLParserTest {

	def url = 'https://fnhp.qhit.net/fn/fundDetail.html?corp=ecza&code=59311102'

	@Test
	void test_queryParameters() {
		def params = URLParser.queryParameters(url)
		assertThat(params.size(), is(2))
		assertTrue(params.containsKey('corp'))
		assertThat(params.corp, is('ecza'))
		assertTrue(params.containsKey('code'))
		assertThat(params.code, is('59311102'))
	}

	@Test
	void test_queryParameter_corp() {
		def corpValue = URLParser.queryParameter(url, 'corp')
		assertThat(corpValue, is('ecza'))
	}

	@Test
	void test_queryParameter_code() {
		def codeValue = URLParser.queryParameter(url, 'code')
		assertThat(codeValue, is('59311102'))
	}
}