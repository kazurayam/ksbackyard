package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*

import static org.junit.Assert.*

import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ksbackyard.RunConfigurationWrapper

@RunWith(JUnit4.class)
class RunConfigurationWrapperTest {

	@Test
	void test_getProjectDir() {
		Path proj = RunConfigurationWrapper.getProjectDir()
		assertThat(proj, is(notNullValue()))
		assertTrue(proj.endsWith(Paths.get("ksbackyard")))
	}

	@Test
	void test_resolveAgainstProjectDir() {
		Path foo = RunConfigurationWrapper.resolveAgainstProjectDir("./tmp/foo.xlsx")
		assertThat(foo, is(notNullValue()))
		assertTrue(foo.endsWith(Paths.get("ksbackyard/tmp/foo.xlsx")))
	}
}

