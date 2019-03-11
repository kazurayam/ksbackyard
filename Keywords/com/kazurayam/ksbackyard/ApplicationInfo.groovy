package com.kazurayam.ksbackyard

import java.util.regex.Pattern
import java.util.regex.Matcher

public class ApplicationInfo {

	/**
	 * ~/.katalon/application.properties file
	 <PRE>
	 #C:\Katalon_Studio_Windows_64-5.10.1
	 #Mon Mar 11 11:27:49 JST 2019
	 katalon.buildNumber=1
	 proxy.serverType=HTTP
	 proxy.username=
	 katalon.versionNumber=5.10.1
	 ntc=0
	 proxy.option=Manual proxy configuration
	 proxy.serverAddress=172.24.2.10
	 ntr=0
	 activated=1015_-403376007
	 proxy.serverPort=8080
	 email=kazurayam@gmail.com
	 npc=0
	 proxy.password=
	 ntcc=196
	 orgTime=1535948278390
	 </PRE>
	 */
	private Properties appProp

	/**
	 * the location of ~/.katalon/application.properities as constant string
	 * 
	 * copied from
	 * public static final String APP_INFO_FILE_LOCATION
	 * in
	 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/constants/StringConstants.java
	 */
	static final String APP_INFO_FILE_LOCATION = System.getProperty("user.home") +
	File.separator + ".katalon" + File.separator + "application.properties"

	static final Pattern KATALON_HOME_PATTERN = Pattern.compile('^#([a-zA-Z]:.*)$')

	private String katalonHome

	/**
	 * copied from 
	 * private static final ThreadLocal<Properties> applicationInfo
	 * in
	 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/configuration/RunConfiguration.java
	 */
	ApplicationInfo() {
		appProp = new Properties();
		File appPropFile = new File(APP_INFO_FILE_LOCATION)
		if (appPropFile.exists()) {
			try {
				appProp.load(new FileInputStream(appPropFile));
			} catch (FileNotFoundException e) {
				// do nothing
			} catch (IOException e) {
				// do nothing
			}
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(appPropFile), "utf-8"))
		String line
		while ((line = br.readLine()) != null) {
			Matcher m = KATALON_HOME_PATTERN.matcher(line)
			if (m.matches()) {
				katalonHome = m.group(1)
			}
		}
	}

	/**
	 * Mimic of DriverFactory.getChromeDriverPath()
	 */
	String getChromeDriverPath() {
		if (OSIdentifier.isWindows()) {
		} else if (OSIdentifier.isMac()) {
		} else if (OSIdentifier.isUnix()) {
		} else {
			throw new IllegalStateException("unsupported OS")
		}
	}

	Properties getAppProp() {
		return this.appProp
	}

	String getKatalonHome() {
		return katalonHome
	}
	
	String getProxyOption() {
		return this.appProp.get('proxy.option')  // Manual proxy configuration
	}
	
	String getProxyServerType() {
		return this.appProp.getProperty('proxy.serverType')  // HTTP
	}
	
	String getProxyServerAddress() {
		return this.appProp.getProperty('proxy.serverAddress')  // 172.24.2.10
	}
	
	String getProxyServerPort() {
		return this.appProp.getProperty('proxy.serverPort')  // 8080
	}
	
	String getProxyUsername() {
		return this.appProp.getProperty('proxy.username')   // can be null
	}
	
	String getProxyPassword() {
		return this.appProp.getProperty('proxy.password')   // can be null
	}
}
