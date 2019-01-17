import com.kazurayam.ksbackyard.ImageCollectionDifferTest

// make the built-in Keywords to emit verbose StackTrace when failed
//CustomKeywords.'com.kazurayam.ksbackyard.junit.WebUIKeywordMainPatch.overrideRunKeyword'()

CustomKeywords.'com.kazurayam.ksbackyard.junit.JUnitCustomKeywords.runWithJUnitRunner'(ImageCollectionDifferTest.class)