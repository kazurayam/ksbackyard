package com.kazurayam.ksbackyard

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite.class)
// please note. Difference of Groovy and Java here.
// In Java, this will be written as @SuiteClasses({...}).
// In Groovy, you should write it @SuiteClasses([...])
// A difference between Java and Groovy is here.
@SuiteClasses([
	AssertTest.class,
	RunConfigurationWrapperTest.class,
	TestObjectSupportTest.class,
	URLParserTest.class,
	VersionTest.class
])
public class AllTests {
}
