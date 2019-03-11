package com.kazurayam.ksbackyard.webdriver

import java.nio.file.Files
import java.nio.file.Path

import groovy.json.JsonSlurper

/**
 *
 * @author kazurayam
 *
 */
class ChromeProfile implements Comparable<ChromeProfile> {

	static final String PREFERENCES_FILE_NAME = 'Preferences'

	private Path profilePath_ = null
	private def preferences_ = null
	private String name_ = null

	ChromeProfile(Path profilePath) {
		Path preferences = profilePath.resolve(PREFERENCES_FILE_NAME)
		if (!Files.exists(preferences)) {
			throw new IOException("${preferences.toString()} is not found")
		}
		this.profilePath_ = profilePath
		JsonSlurper slurper = new JsonSlurper()
		this.preferences_ = slurper.parse(preferences.toFile())
		this.name_ = this.preferences_['profile']['name']
	}

	String getName() {
		return this.name_
	}

	def getPreferences() {
		return this.preferences_
	}

	Path getProfilePath() {
		return this.profilePath_
	}

	@Override
	int compareTo(ChromeProfile other) {
		return this.getName().compareTo(other.getName())
	}
}