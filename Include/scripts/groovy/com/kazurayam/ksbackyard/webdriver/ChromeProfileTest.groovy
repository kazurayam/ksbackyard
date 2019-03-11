package com.kazurayam.ksbackyard.webdriver

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import java.nio.file.Path

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @author urayamakazuaki
 *
 */
@RunWith(JUnit4.class)
public class ChromeProfileTest {

	@Test
	void test_ChromeProfile() {
		// when:
		Path defaultProfileDirectory = ChromeDriverFactory.getChromeUserDataDirectory().resolve('Default')
		ChromeProfile defaultProfile = new ChromeProfile(defaultProfileDirectory)
		// then:
		assertThat(defaultProfile, is(notNullValue()))
		assertThat(defaultProfile.getName(), is(notNullValue()))
		assertThat(defaultProfile.getPreferences(), is(notNullValue()))
		assertThat(defaultProfile.getProfilePath(), is(defaultProfileDirectory))
	}
}
