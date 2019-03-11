import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.constants.StringConstants

String chromeDriverPath1 = DriverFactory.getChromeDriverPath()
WebUI.comment("chromeDriverPath1=${chromeDriverPath1}")

String chromeDriverPath2 = RunConfiguration.getDriverSystemProperty(DriverFactory.WEB_UI_DRIVER_PROPERTY, DriverFactory.CHROME_DRIVER_PATH_PROPERTY)
WebUI.comment("chromeDriverPath2=${chromeDriverPath2}")
WebUI.comment("DriverFactory.WEB_UI_DRIVER_PROPERTY=${DriverFactory.WEB_UI_DRIVER_PROPERTY}")
WebUI.comment("DriverFactory.CHROME_DRIVER_PATH_PROPERTY=${DriverFactory.CHROME_DRIVER_PATH_PROPERTY}")
