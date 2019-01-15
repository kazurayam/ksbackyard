package com.kazurayam.ksbackyard

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite.class)
@SuiteClasses([
	//AssertTest.class,
	RunConfigurationWrapperTest.class,
	URLParserTest.class,
	VersionTest.class
])
// please note. Difference of Groovy and Java here.
// In Java, this will be written as @SuiteClasses({...}). Difference of Java and Groovy here.
public class AllTests {
}
