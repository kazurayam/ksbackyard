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
	ImageCollectionDifferTest.class,
	QuietBrowserTest.class,
	RunConfigurationWrapperTest.class,
	ScreenshotDriverCompareImagesTest.class,
	ScreenshotDriverTest.class,
	TestObjectSupportTest.class,
	URLParserTest.class,
	VersionTest.class
])
public class AllTests {
}
