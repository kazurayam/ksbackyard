package com.kazurayam.ksbackyard.test

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

import org.junit.Test

import com.kazurayam.ksbackyard.Version
public class VersionTest {

	@Test
	void test_getVersion() {
		String version = Version.getVersion()
		assertNotNull("Version.getVersion() returned null", version)
		assertTrue("version:\'${}\' should starts with ksbackyard", version.contains('ksbackyard'))
	}

}
