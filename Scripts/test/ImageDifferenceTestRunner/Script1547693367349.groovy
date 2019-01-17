import com.kazurayam.ksbackyard.ImageDifferenceTest

// make the built-in Keywords to emit verbose StackTrace when failed
//CustomKeywords.'com.kazurayam.ksbackyard.junit.WebUIKeywordMainPatch.overrideRunKeyword'()

CustomKeywords.'com.kazurayam.ksbackyard.junit.JUnitCustomKeywords.runWithJUnitRunner'(ImageDifferenceTest.class)