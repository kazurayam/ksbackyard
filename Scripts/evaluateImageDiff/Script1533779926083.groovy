import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.Screenshot
import com.kazurayam.material.MaterialRepository

String TS_expectedTimestamp = '20180809_102947'
String TS_actualTimestamp   = '20180809_103122'

Path materialsDir = Paths.get(System.getProperty('user.dir')).resolve('Materials')
Path expectedImageFile = materialsDir.resolve("TS1/${TS_expectedTimestamp}/TC1/CURA_Healthcare_Searvice.png")
Path actualImageFile   = materialsDir.resolve("TS1/${TS_actualTimestamp}/TC1/CURA_Healthcare_Searvice.png")
BufferedImage expectedImage = ImageIO.read(expectedImageFile.toFile())
BufferedImage actualImage   = ImageIO.read(actualImageFile.toFile())
Screenshot expectedScreenshot = new Screenshot(expectedImage)
Screenshot actualScreenshot   = new Screenshot(actualImage)
// Imageの差分を取得する
ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot)

WebUI.comment(">>> diff.hasDiff() returned ${diff.hasDiff()}")
WebUI.comment(">>> diff.getDiffSize() returned ${diff.getDiffSize()}")
def area = actualImage.getWidth() * actualImage.getHeight()
WebUI.comment(">>> area is ${area}")
def diffRatio = String.format("%.2f", diff.getDiffSize() / area * 100)
WebUI.comment(">>> diff ratio is ${diffRatio}% (diff.getDiffSize()/ratio * 100)" )

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

Path pngFile

// 
BufferedImage markedImage = diff.getMarkedImage()
pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID, 'CURA_Healthcare_Searvice.markedImage.png')
ImageIO.write(markedImage, "PNG", pngFile.toFile())

//
BufferedImage transparentMarkedImage = diff.getTransparentMarkedImage()
pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID, 'CURA_Healthcare_Searvice.transparentMarkedImage.png')
ImageIO.write(markedImage, "PNG", pngFile.toFile())
