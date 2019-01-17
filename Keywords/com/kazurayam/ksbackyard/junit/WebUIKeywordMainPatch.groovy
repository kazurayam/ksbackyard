package com.kazurayam.ksbackyard.junit

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.internal.ExceptionsUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain

public class WebUIKeywordMainPatch {

	/**
	 * https://forum.katalon.com/t/cannot-find-elements-when-xpath-expression-is-null/13840/16
	 */
	@Keyword
	static void overrideRunKeyword() {
		WebUIKeywordMain.metaClass.static.runKeyword = { Closure closure, FailureHandling flowControl, boolean takeScreenShot, String errorMessage ->
			try {
				return closure.call();
			} catch (Throwable e) {
				if (isPageLoadTimeoutException(e) && DriverFactory.isEnablePageLoadTimeout() && DriverFactory.isIgnorePageLoadTimeoutException()) {
					stepFailed(errorMessage, FailureHandling.OPTIONAL, ExceptionsUtil.getMessageForThrowable(e), takeScreenShot);
					return;
				}
				// kazurayam wants to insert a line here
				e.printStackTrace()

				stepFailed(errorMessage, flowControl, ExceptionsUtil.getMessageForThrowable(e), takeScreenShot);
			}
		}
	}
}
