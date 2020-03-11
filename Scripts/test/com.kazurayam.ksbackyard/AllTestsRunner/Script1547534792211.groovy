import static com.kazurayam.junit4ks.JUnitCustomKeywords.runWithJUnitRunner
import static com.kazurayam.junit4ks.JUnitCustomKeywords.runWithJUnitRunner

import com.kazurayam.ksbackyard.AssertTest
import com.kazurayam.ksbackyard.ImageCollectionDifferTest
import com.kazurayam.ksbackyard.OSIdentifierTest
import com.kazurayam.ksbackyard.QuietBrowserTest
import com.kazurayam.ksbackyard.RunConfigurationWrapperTest
import com.kazurayam.ksbackyard.ScreenshotDriverCensoringTest
import com.kazurayam.ksbackyard.ScreenshotDriverCompareImagesTest
import com.kazurayam.ksbackyard.ScreenshotDriverOptionsTest
import com.kazurayam.ksbackyard.ScreenshotDriverResisingTest
import com.kazurayam.ksbackyard.ScreenshotDriverTest
import com.kazurayam.ksbackyard.TestObjectSupportTest
import com.kazurayam.ksbackyard.URLParserTest
import com.kazurayam.ksbackyard.VersionTest


runWithJUnitRunner(AssertTest.class)
runWithJUnitRunner(ImageCollectionDifferTest.class)
runWithJUnitRunner(OSIdentifierTest.class)
runWithJUnitRunner(QuietBrowserTest.class)
runWithJUnitRunner(RunConfigurationWrapperTest.class)
runWithJUnitRunner(ScreenshotDriverCensoringTest.class)
runWithJUnitRunner(ScreenshotDriverCompareImagesTest.class)
runWithJUnitRunner(ScreenshotDriverOptionsTest.class)
runWithJUnitRunner(ScreenshotDriverResisingTest.class)
runWithJUnitRunner(ScreenshotDriverTest.class)
runWithJUnitRunner(TestObjectSupportTest.class)
runWithJUnitRunner(URLParserTest.class)
runWithJUnitRunner(VersionTest.class)


