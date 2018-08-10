package internal

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */
public class GlobalVariable {
     
    /**
     * <p></p>
     */
    public static Object URL
     
    /**
     * <p></p>
     */
    public static Object MATERIAL_REPOSITORY
     
    /**
     * <p></p>
     */
    public static Object CURRENT_TESTCASE_ID
     

    static {
        def allVariables = [:]        
        allVariables.put('default', ['URL' : 'https://katalon-demo-cura.herokuapp.com', 'MATERIAL_REPOSITORY' : null, 'CURRENT_TESTCASE_ID' : ''])
        allVariables.put('develop', allVariables['default'] + ['URL' : 'http://demoaut-mimic.kazurayam.com', 'MATERIAL_REPOSITORY' : null, 'CURRENT_TESTCASE_ID' : ''])
        allVariables.put('product', allVariables['default'] + ['URL' : 'https://katalon-demo-cura.herokuapp.com', 'MATERIAL_REPOSITORY' : null, 'CURRENT_TESTCASE_ID' : ''])
        
        String profileName = RunConfiguration.getExecutionProfile()
        
        def selectedVariables = allVariables[profileName]
        URL = selectedVariables['URL']
        MATERIAL_REPOSITORY = selectedVariables['MATERIAL_REPOSITORY']
        CURRENT_TESTCASE_ID = selectedVariables['CURRENT_TESTCASE_ID']
        
    }
}
