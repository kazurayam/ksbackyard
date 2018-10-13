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
import com.kazurayam.materials.MaterialRepository
import com.kazurayam.materials.MaterialRepositoryFactory

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

	/**
	 * test makeDiffs(String profileExpected = 'product',
	 String profileActual = 'develop',
	 String tSuiteName,
	 String tCaseName,
	 Double criteriaPercent = 3.0) {
	 */
	@Test
	void test_makeDiff() {
		Path materials = fixture.resolve('Materials')
		MaterialRepository mr = MaterialRepositoryFactory.createInstance(materials)
		mr.putCurrentTestSuite('Test Suites/Main/TS1', '20181013_141200')
		//
		ImageCollectionDiffer icd = new ImageCollectionDiffer(mr)
		icd.makeDiffs('product', 'develop',
				'Test Suites/Main/TS1', 'Test Cases/ImageDiff', 3.0)
		//
	}
}
