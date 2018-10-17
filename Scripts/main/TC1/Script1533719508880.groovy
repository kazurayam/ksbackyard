import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path

import com.kazurayam.materials.MaterialRepository
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.setViewPortSize(1024, 768)

WebUI.navigateToUrl(GlobalVariable.URL)

WebUI.verifyElementPresent(findTestObject('Page_CURA Healthcare Service/a_Make Appointment'),
	10, FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

Path pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID, 'CURA_Healthcare_Service.png')

CustomKeywords.'com.kazurayam.ksbackyard.ScreenshotDriver.saveEntirePageImage'(
	pngFile.toFile(), 500)

WebUI.closeBrowser()

