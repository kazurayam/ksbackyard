import com.kms.katalon.core.model.FailureHandling

def expected = "Ken"
def actual  = "Marry"

CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertTrue'(
	"${expected} is not ${actual}", expected == actual,
	FailureHandling.OPTIONAL)

expected = "Tom"
actual   = "Jerry"
CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertEquals'(
	"${expected} is not equal to ${actual}", expected, actual,
	FailureHandling.CONTINUE_ON_FAILURE)

expectedNumber = 0
actualNumber   = 999
CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertEquals'(
	"${expectedNumber} is not equal to ${actualNumber}", expectedNumber, actualNumber,
	FailureHandling.STOP_ON_FAILURE)