package com.kazurayam.ksbackyard.test

import java.nio.file.Path
import java.nio.file.Paths

import org.apache.commons.io.FileUtils
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.kazurayam.ksbackyard.ImageCollectionDiffer
import com.kazurayam.materials.ExecutionProfile
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory
import com.kazurayam.materials.TCaseName
import com.kazurayam.materials.TSuiteName

class ImageCollectionDifferTest {

	static Path source  = Paths.get('./src/test/resources/fixture')
	static Path fixture = Paths.get('./tmp/ImageCollectionDifferTest')

	@BeforeClass
	static void beforeClass() {
		FileUtils.deleteDirectory(fixture.toFile())
		FileUtils.copyDirectory(source.toFile(), fixture.toFile())
	}

	@Before
	void before() {
	}

	@After
	void after() {}

	@AfterClass
	static void afterClass() {}

	@Test
	void test_makeDiff() {
		Path materials = fixture.resolve('Materials')
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materials)
		mr.putCurrentTestSuite('Test Suites/ImageDiff', '20181014_060501')
		//
		ImageCollectionDiffer icd = new ImageCollectionDiffer(mr)
		icd.makeDiffs(
				new ExecutionProfile('product'),
				new ExecutionProfile('develop'),
				new TSuiteName('Test Suites/Main/TS1'),
				new TCaseName('Test Cases/ImageDiff'),
				7.0)
		//
	}
}
