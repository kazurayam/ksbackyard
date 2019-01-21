package com.kazurayam.imagedifference

import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import javax.imageio.ImageIO

import org.apache.commons.io.FileUtils
import org.junit.BeforeClass
import org.junit.Test

class ImageDifferenceSerializerTest {

	private static Path projectDir
	private static File image1_
	private static File image6_
	private static Path testOutputDir

	@BeforeClass
	static void beforeClassSpec() {
		projectDir = Paths.get(".")
		Path fixtureImagesDir = projectDir.resolve("Include/resources/fixture/images")
		image1_ = fixtureImagesDir.resolve("andrej.png").toFile()
		image6_ = fixtureImagesDir.resolve("kazurayam.png").toFile()
		testOutputDir = projectDir.resolve("tmp/testOutput")
	}

	@Test
	void testSimilarImages() {
		//setup:
		Path output = testOutputDir.resolve(ImageDifferenceSerializerTest.class.getName() + "/testSimilarImages")
		Files.createDirectories(output)
		FileUtils.deleteQuietly(output.toFile())
		//when:
		BufferedImage bix = ImageIO.read(image6_)
		BufferedImage biy = ImageIO.read(image6_)
		ImageDifference imageDifference = new ImageDifference(bix, biy)
		ImageDifferenceSerializer serializer = new ImageDifferenceSerializer(imageDifference, output, "profile")
		serializer.serialize()
		Path expectedFile = output.resolve("profile.expected.png")
		Path actualFile = output.resolve("profile.actual.png")
		Path diffFile = output.resolve("profile.diff(0.00).png")
		//then:
		expectedFile.toFile().exists()
		actualFile.toFile().exists()
		diffFile.toFile().exists()
	}

	def testDifferentImages() {
		//setup:
		Path output = testOutputDir.resolve(ImageDifferenceSerializerTest.class.getName() + "/testDifferentImages")
		Files.createDirectories(output)
		FileUtils.deleteQuietly(output.toFile())
		//when:
		BufferedImage bix = ImageIO.read(image1_)
		BufferedImage biy = ImageIO.read(image6_)
		ImageDifference imageDifference = new ImageDifference(bix, biy)
		ImageDifferenceSerializer serializer = new ImageDifferenceSerializer(imageDifference, output, "profile")
		serializer.serialize()
		Path expectedFile = output.resolve("profile.expected.png")
		Path actualFile = output.resolve("profile.actual.png")
		Path diffFile = output.resolve("profile.diff(79.11).png")
		//then:
		expectedFile.toFile().exists()
		actualFile.toFile().exists()
		diffFile.toFile().exists()
	}
}