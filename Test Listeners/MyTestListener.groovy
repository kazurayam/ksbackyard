import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import java.nio.file.Path
import java.nio.file.Paths
import com.kazurayam.material.Helpers
import com.kazurayam.material.MaterialRepository
import com.kazurayam.material.MaterialRepositoryFactory
import com.kms.katalon.core.configuration.RunConfiguration

class MyTestListener {
	
	static Path reportDir
	static Path materialsFolder
	
	static {
		// for example, reportDir = C:/Users/username/katalon-workspace/ImageDiffByAShot/Reports/TS1/20180618_165141
		reportDir = Paths.get(RunConfiguration.getReportFolder())
		materialsFolder = Paths.get(RunConfiguration.getProjectDir()).resolve('Materials')
		// for example, materialsFolder == C:/Users/username/katalon-workspace/ImageDiffByAShot/Materials
		Helpers.ensureDirs(materialsFolder)
	}
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		def testSuiteId = testSuiteContext.getTestSuiteId()
		def testSuiteTimestamp = reportDir.getFileName().toString()    // e.g., '20180618_165141'
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsFolder)
		mr.putCurrentTestSuite(testSuiteId, testSuiteTimestamp)	
		GlobalVariable.MATERIAL_REPOSITORY = mr
		
		WebUI.comment(">>> testSuiteId is '${testSuiteId}', testSuiteTimestamp is '${testSuiteTimestamp}'")
		WebUI.comment(">>> Instance of MaterialRepository(${mr.getBaseDir().toString()})" +
			" is set to GlobalVariable.MATERIAL_REPOSITORY")
	}
		
		
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext relate information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		if (GlobalVariable.MATERIAL_REPOSITORY == null) {
			MaterialRepository mr = MaterialRepositoryFactory.createInstance(materialsFolder)
			GlobalVariable.MATERIAL_REPOSITORY = mr
		}
		GlobalVariable.CURRENT_TESTCASE_ID = testCaseContext.getTestCaseId()   //  e.g., 'Test Cases/TC1'
	}

}