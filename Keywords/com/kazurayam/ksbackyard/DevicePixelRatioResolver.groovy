package com.kazurayam.ksbackyard

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

/**
 * find out the Device Pixel Ratio of the current platform 
 * by making query for the DPR value to web browser through the WebDriver API.
 * 
 * What is Device Pixel Ratio?
 * See https://contest-server.cs.uchicago.edu/ref/JavaScript/developer.mozilla.org/en-US/docs/Web/API/Window/devicePixelRatio.html
 * >In simpler terms, DPR tells the browser how many of the screen's actual pixels should be used to draw a single CSS pixel.
 * 
 * @author kazurayam
 */
public class DevicePixelRatioResolver {

	private DevicePixelRatioResolver() {
		throw new UnsupportedOperationException()
	}

	public static Float resolveDPR(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver
		String value = js.executeScript("return window.devicePixelRatio")
		return Float.parseFloat(value)
	}
}
