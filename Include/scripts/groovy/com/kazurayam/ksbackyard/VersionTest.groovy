package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class VersionTest {

	@Test
	void testGetVersion() {
		def expected = 'ksbackyard 0.19.0'
		def actual = Version.getVersion()
		assertThat(actual, is(expected))
	}
}
