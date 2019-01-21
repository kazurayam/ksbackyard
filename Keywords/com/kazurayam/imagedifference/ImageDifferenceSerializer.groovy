package com.kazurayam.imagedifference

import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

/**
 * encloses 3 Path objects; expected, actual and diff
 * resolves 3 file paths, writes images into files
 *
 * @author kazurayam
 *
 */
class ImageDifferenceSerializer {

	private ImageDifference imgDifference_
	private Path outputDirectory_
	private Path expected_
	private Path actual_
	private Path diff_

	ImageDifferenceSerializer(ImageDifference imgDifference, Path outputDirectory, String identifier) {
		imgDifference_ = imgDifference
		outputDirectory_ = outputDirectory
		expected_ = outputDirectory.resolve(identifier + ".expected.png")
		actual_   = outputDirectory.resolve(identifier + ".actual.png")
		diff_     = outputDirectory.resolve(identifier + ".diff(${imgDifference.getRatioAsString()}).png")
	}

	Path getExpected() {
		return expected_
	}

	Path getActual() {
		return actual_
	}

	Path getDiff() {
		return diff_
	}

	void serialize() {
		Files.createDirectories(outputDirectory_)
		ImageIO.write(imgDifference_.getExpectedImage(), "PNG", expected_.toFile())
		ImageIO.write(imgDifference_.getActualImage(),   "PNG", actual_.toFile())
		ImageIO.write(imgDifference_.getDiffImage(),     "PNG", diff_.toFile())
	}
}
