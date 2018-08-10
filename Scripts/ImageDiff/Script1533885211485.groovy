import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import org.junit.After

import com.kazurayam.material.MaterialRepository
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer

import com.kazurayam.material.Material
import com.kazurayam.material.MaterialPair

String profileExpected   = 'product'
String profileActual     = 'develop'
String tSuiteName        = 'TS1'

List<MaterialPair> materialPairs = CustomKeywords.'com.kazurayam.ksbackyard.MaterialsWrapper.getScreenshotPairs'(
	profileExpected, profileActual, 'TS1')

CustomKeywords.'com.kazurayam.ksbackyard.Assert.assertTrue'(
	">>> materialPairs.size() is 0", materialPairs.size() > 0)

MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
assert mr != null

for (MaterialPair pair : materialPairs) {
	Material expMate = pair.getExpected()
	Material actMate = pair.getActual()
	BufferedImage expectedImage = ImageIO.read(expMate.getPath().toFile())
	BufferedImage actualImage   = ImageIO.read(actMate.getPath().toFile())
	Screenshot expectedScreenshot = new Screenshot(expectedImage)
	Screenshot actualScreenshot   = new Screenshot(actualImage)
	// Imageの差分を取得する
	ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot)
	// ImageDiffをファイルに保存する
	BufferedImage markedImage = diff.getMarkedImage()
	String fileName = expMate.getPath().getFileName().toString()
	String fileId = fileName.substring(0, fileName.lastIndexOf('.'))
	String expTimestamp = expMate.getParent().getParent().getTSuiteTimestamp().format()
	String actTimestamp = actMate.getParent().getParent().getTSuiteTimestamp().format()
	Path pngFile = mr.resolveMaterialPath(
		GlobalVariable.CURRENT_TESTCASE_ID,
		"${fileId}.${expTimestamp}${profileExpected}-${actTimestamp}${profileActual}.png")
	ImageIO.write(markedImage, "PNG", pngFile.toFile())
	// 差分の大きさを評価して、もし違いが基準より大きいならFAILさせる
	def result = CustomKeywords.'com.kazurayam.ksbackyard.AShotDriver.hasSignificantDiff'(diff, 3.0)
}
