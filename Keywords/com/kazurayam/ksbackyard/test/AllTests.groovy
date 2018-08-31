package com.kazurayam.ksbackyard.test

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite.class)
@SuiteClasses([
	AssertTest.class, QuietBrowserTest.class, RunConfigurationWrapperTest.class, URLParserTest.class
])
// please note. Difference of Groovy and Java here.
// In Java, this will be written as @SuiteClasses({...}). Difference of Java and Groovy here.
public class AllTests {
}
