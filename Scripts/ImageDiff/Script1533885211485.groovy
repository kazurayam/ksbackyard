import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import com.kazurayam.material.MaterialRepository
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY

String tSuiteName = 'TS1'
String tCaseName  = 'TC1'
String TS_expectedTimestamp = '20180810_140105'
String TS_expectedProfile   = 'product'
String TS_actualTimestamp   = '20180810_140106'
String TS_actualProfile     = 'demo'
String pageTitle = 'CURA_Healthcare_Service'

Path materialsDir = Paths.get(System.getProperty('user.dir')).resolve('Materials')
Path expectedImageFile = materialsDir.resolve("${tSuiteName}/${TS_expectedTimestamp}/${tCaseName}/${pageTitle}.png")
Path actualImageFile   = materialsDir.resolve("${tSuiteName}/${TS_actualTimestamp}/${tCaseName}/${pageTitle}.png")
CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertTrue'(
	"${expectedImageFile} does not exist", Files.exists(expectedImageFile))
CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertTrue'(
	"${actualImageFile} does not exist", Files.exists(actualImageFile))
BufferedImage expectedImage = ImageIO.read(expectedImageFile.toFile())
BufferedImage actualImage   = ImageIO.read(actualImageFile.toFile())
Screenshot expectedScreenshot = new Screenshot(expectedImage)
Screenshot actualScreenshot   = new Screenshot(actualImage)
// Imageの差分を取得する
ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot)

// ImageDiffをファイルに保存する
BufferedImage markedImage = diff.getMarkedImage()
Path pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID,
	"${pageTitle}.${TS_expectedTimestamp}${TS_expectedProfile}-${TS_actualTimestamp}${TS_actualProfile}.png")
ImageIO.write(markedImage, "PNG", pngFile.toFile())


// 差分を評価する
def result = CustomKeywords.'com.kazurayam.ksbackyard.AShotDriver.hasSignificantDiff'(diff, 3.0)
