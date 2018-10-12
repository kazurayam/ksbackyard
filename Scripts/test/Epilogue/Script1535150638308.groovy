import static com.kazurayam.ksbackyard.Assert.assertTrue

import org.junit.runner.notification.Failure

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 */
WebUI.comment(">>> Run:${result.getRunCount()}, Failure:${result.getFailureCount()}, Ignored:${result.getIgnoreCount()}")

StringBuilder sb = new StringBuilder()
List<Failure> failures = result.getFailures()
for (Failure failure : failures) {
	sb.append('-' * 80)
	sb.append("\n")
	sb.append(failure.getDescription().getDisplayName() + "\n")
	sb.append(failure.getMessage() + "\n")
	sb.append(failure.getTrace() + "\n")
}
WebUI.comment(sb.toString())

assertTrue("${result.getFailureCount()} test${result.getFailureCount() > 1 ? 's' : ''} failed",
	result.getFailureCount() == 0,
	FailureHandling.CONTINUE_ON_FAILURE
	)