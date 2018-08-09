import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import com.kazurayam.material.MaterialRepository
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer

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

// ImageDiffをファイルに保存する
BufferedImage markedImage = diff.getMarkedImage()
MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
Path pngFile = mr.resolveMaterialPath(
	GlobalVariable.CURRENT_TESTCASE_ID,
	'CURA_Healthcare_Searvice.markedImage.png')
ImageIO.write(markedImage, "PNG", pngFile.toFile())


// 差分を評価する
def result = CustomKeywords.'com.kazurayam.ksbackyard.AShotDriver.hasSignificantDiff'(diff, 3.0)
