import com.kazurayam.ksbackyard.ScreenshotDriverCompareImagesTest

// make the built-in Keywords to emit verbose StackTrace when failed
CustomKeywords.'com.kazurayam.ksbackyard.junit.WebUIKeywordMainPatch.overrideRunKeyword'()

// do the job
CustomKeywords.'com.kazurayam.ksbackyard.junit.JUnitCustomKeywords.runWithJUnitRunner'(ScreenshotDriverCompareImagesTest.class)