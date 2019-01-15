package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class URLParserTest {

	def url = 'https://foo.bar.com/app.html?corp=efgh&code=59311102'

	@Test
	void test_queryParameters() {
		def params = URLParser.queryParameters(url)
		//println "params: ${params}"
		assertThat(params.size(), is(2))
		assertTrue(params.containsKey('corp'))
		assertThat(params.corp, is('efgh'))
		assertTrue(params.containsKey('code'))
		assertThat(params.code, is('59311102'))
	}

	@Test
	void test_queryParameter_corp() {
		def corpValue = URLParser.queryParameter(url, 'corp')
		assertThat(corpValue, is('efgh'))
	}

	@Test
	void test_queryParameter_code() {
		def codeValue = URLParser.queryParameter(url, 'code')
		assertThat(codeValue, is('59311102'))
	}
}