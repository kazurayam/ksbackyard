package com.kazurayam.ksbackyard

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
public class OSIdentifierTest {

	@Test
	void test_anyOneOfThem() {
		boolean isWindows = OSIdentifier.isWindows()
		boolean isMac = OSIdentifier.isMac()
		boolean isUnix = OSIdentifier.isUnix()
		boolean isSolaris = OSIdentifier.isSolaris()
		assertThat(isWindows || isMac || isUnix || isSolaris, is(true))
	}
}
