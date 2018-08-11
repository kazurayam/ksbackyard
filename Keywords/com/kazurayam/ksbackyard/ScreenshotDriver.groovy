package com.kazurayam.ksbackyard

import static com.kazurayam.ksbackyard.Assert.assertTrue

import java.awt.image.BufferedImage
import java.nio.file.Path
import java.util.stream.Collectors

import javax.imageio.ImageIO

import org.openqa.selenium.WebDriver

import com.kazurayam.material.FileType
import com.kazurayam.material.Material
import com.kazurayam.material.MaterialPair
import com.kazurayam.material.MaterialRepository
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.shooting.ShootingStrategies


public class ScreenshotDriver {

	static MaterialRepository mr_ = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY

	static {
		assert mr_ != null
	}

	@Keyword
	static void takeEntirePage(WebDriver webDriver, File file) {
		Screenshot screenshot = new AShot().
				shootingStrategy(ShootingStrategies.viewportPasting(100)).
				takeScreenshot(webDriver)
		ImageIO.write(screenshot.getImage(), "PNG", file)
	}


	@Keyword
	static Float diffRatioPercent(ImageDiff diff) {
		boolean hasDiff = diff.hasDiff()
		if (!hasDiff) {
			return 0.0
		}
		int diffSize = diff.getDiffSize()
		int area = diff.getMarkedImage().getWidth() * diff.getMarkedImage().getHeight()
		Float diffRatio = diff.getDiffSize() / area * 100
		return diffRatio
	}

	@Keyword
	static boolean hasSignificantDiff(ImageDiff diff, Float criteriaPercent) {
		Float diffRatio = ScreenshotDriver.diffRatioPercent(diff)
		if (diffRatio > criteriaPercent) {
			KeywordUtil.markFailed("diffRatio = ${diffRatio} is exceeding criteria = ${criteriaPercent}")
		}
	}



	@Keyword
	static def makeDiffs(String profileExpected = 'product', String profileActual = 'develop', String tSuiteName, Float criteriaPercent = 3.0) {

		if (tSuiteName == null) {
			throw new IllegalArgumentException('#doDiff argument tSuiteName is required')
		}

		List<MaterialPair> materialPairs = ScreenshotDriver.getScreenshotPairs(profileExpected, profileActual, tSuiteName)
		Assert.assertTrue(">>> materialPairs.size() is 0", materialPairs.size() > 0)

		MaterialRepository mr = (MaterialRepository)GlobalVariable.MATERIAL_REPOSITORY
		assertTrue(">>> GlobalVariable.MATERIAL_REPOSITORY is null", mr != null)

		for (MaterialPair pair : materialPairs) {
			Material expMate = pair.getExpected()
			Material actMate = pair.getActual()
			BufferedImage expectedImage = ImageIO.read(expMate.getPath().toFile())
			BufferedImage actualImage   = ImageIO.read(actMate.getPath().toFile())
			Screenshot expectedScreenshot = new Screenshot(expectedImage)
			Screenshot actualScreenshot   = new Screenshot(actualImage)
			// get diff of the pair of images
			ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot)

			// get diffRatioPercent
			Float diffRatioPercent = ScreenshotDriver.diffRatioPercent(diff)

			// save the diff image into file
			BufferedImage markedImage = diff.getMarkedImage()
			String fileName = expMate.getPath().getFileName().toString()
			String fileId = fileName.substring(0, fileName.lastIndexOf('.'))
			String expTimestamp = expMate.getParent().getParent().getTSuiteTimestamp().format()
			String actTimestamp = actMate.getParent().getParent().getTSuiteTimestamp().format()
			Path pngFile = mr.resolveMaterialPath(
					GlobalVariable.CURRENT_TESTCASE_ID,
					"${fileId}.${expTimestamp}_${profileExpected}-${actTimestamp}_${profileActual}" +
					".(${String.format('%.2f', diffRatioPercent)}).png")
			ImageIO.write(markedImage, "PNG", pngFile.toFile())

			// verify the diff-ratio, fail the test if the ratio is greater than criteria
			if (diffRatioPercent > criteriaPercent) {
				KeywordUtil.markFailed("diffRatio = ${diffRatioPercent} is exceeding criteria = ${criteriaPercent}")
			}
		}
	}


	static List<MaterialPair> getScreenshotPairs(
			String expectedProfile /* 'product' */,
			String actualProfile   /* 'develop' */,
			String testSuiteId     /* 'Test Suites/TS1' */) {

		List<MaterialPair> list = mr_.getRecentMaterialPairs(expectedProfile, actualProfile, testSuiteId)
		KeywordUtil.logInfo(">>> list.size() is ${list.size()}")
		List<MaterialPair> result = list.stream().filter { mp ->
			mp.getLeft().getFileType() == FileType.PNG
		}.collect(Collectors.toList())
		KeywordUtil.markPassed("returning MaterialPairs successfully")
		return result
	}
}
