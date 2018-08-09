import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Path

import com.kazurayam.material.MaterialRepository
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(GlobalVariable.URL)

WebUI.verifyElementPresent(findTestObject('Page_CURA Healthcare Service/a_Make Appointment'),
	10, FailureHandling.STOP_ON_FAILURE)

WebUI.delay(1)

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

Path pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID, 'CURA_Healthcare_Searvice.png')

CustomKeywords.'com.kazurayam.ksbackyard.AShotDriver.takeEntirePage'(
	DriverFactory.getWebDriver(), pngFile.toFile())


WebUI.closeBrowser()

