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

import com.kazurayam.material.Material
import com.kazurayam.material.MaterialPair

CustomKeywords.'com.kazurayam.ksbackyard.ScreenshotDriver.makeDiffs'('product', 'develop', 'TS1', 3.0)
