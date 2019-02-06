package com.kazurayam.ksbackyard

import com.kms.katalon.core.testobject.TestObject

import groovy.json.JsonOutput

/**
 * Container of parameters to saveEntirePageImage() method and
 * takeEntierPageImage() methods of ScreenshotDriver
 * 
 * employs the Builder Pattern described by the Effective Java book. 
 * @author kazurayam
 */
public class ScreenshotDriverOptions {

	private int timeout
	private List<TestObject> ignoredElements

	static class Builder {
		
		private int timeout
		private List<TestObject> ignoredElements
		
		Builder() {
			timeout = 300   // default is 300 milli seconds
			ignoredElements = new ArrayList<TestObject>()   // no elements to ignore
		}
		Builder timeout(int value) {
			if (value < 0) throw new IllegalArgumentException("value(${value}) must not be negative")
			if (value > 1000) throw new IllegalArgumentException("value(${value}) is regared milli-seconds.")
			this.timeout = value
			return this
		}
		Builder addIgnoredElement(TestObject testObject) {
			Objects.requireNonNull(testObject, "testObject must not be null")
			this.ignoredElements.add(testObject)
			return this
		}
		ScreenshotDriverOptions build() {
			return new ScreenshotDriverOptions(this)
		}
	}
	
	private ScreenshotDriverOptions(Builder builder) {
		this.timeout = builder.timeout
		this.ignoredElements = builder.ignoredElements
	}
	
	int getTimeout() {
		return this.timeout
	}
	
	List<TestObject> getIgnoredElements() {
		return this.ignoredElements
	}
	
	@Override
	String toString() {
		String s = JsonOutput.toJson(this)
		String pp = JsonOutput.prettyPrint(s)
		return pp
	}
}
