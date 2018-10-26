package com.kazurayam.ashot

import static java.util.Collections.singletonList;
import static ru.yandex.qatools.ashot.coordinates.CoordsPreparationStrategy.intersectingWith;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot
import ru.yandex.qatools.ashot.coordinates.*;
import ru.yandex.qatools.ashot.cropper.DefaultCropper;
import ru.yandex.qatools.ashot.cropper.ImageCropper;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.SimpleShootingStrategy;

import com.kazurayam.ashot.cropper.DefaultCropper_mock;

public class AShotMock extends AShot implements Serializable {

	private CoordsProvider coordsProvider = new WebDriverCoordsProvider();
	private ImageCropper cropper = new DefaultCropper_mock();
	private Set<By> ignoredLocators = new HashSet<>();
	private Set<Coords> ignoredAreas = new HashSet<>();
	private ShootingStrategy shootingStrategy = new SimpleShootingStrategy();

	public AShot coordsProvider(final CoordsProvider coordsProvider) {
		this.coordsProvider = coordsProvider;
		return this;
	}

	@SuppressWarnings("UnusedDeclaration")
	public AShot imageCropper(ImageCropper cropper) {
		this.cropper = cropper;
		return this;
	}

	/**
	 * Sets the list of locators to ignore during image comparison.
	 *
	 * @param ignoredElements list of By
	 * @return this
	 */
	@SuppressWarnings("UnusedDeclaration")
	public synchronized AShot ignoredElements(final Set<By> ignoredElements) {
		this.ignoredLocators = ignoredElements;
		return this;
	}

	/**
	 * Adds selector of ignored element.
	 *
	 * @param selector By
	 * @return this
	 */
	public synchronized AShot addIgnoredElement(final By selector) {
		this.ignoredLocators.add(selector);
		return this;
	}

	/**
	 * Sets a collection of wittingly ignored coords.
	 * @param ignoredAreas Set of ignored areas
	 * @return aShot
	 */
	@SuppressWarnings("UnusedDeclaration")
	public synchronized AShot ignoredAreas(final Set<Coords> ignoredAreas) {
		this.ignoredAreas = ignoredAreas;
		return this;
	}

	/**
	 * Adds coordinated to set of wittingly ignored coords.
	 * @param area coords of wittingly ignored coords
	 * @return aShot;
	 */
	@SuppressWarnings("UnusedDeclaration")
	public synchronized AShot addIgnoredArea(Coords area) {
		this.ignoredAreas.add(area);
		return this;
	}

	/**
	 * Sets the policy of taking screenshot.
	 *
	 * @param strategy shooting strategy
	 * @return this
	 * @see ru.yandex.qatools.ashot.shooting.ShootingStrategy
	 */
	public AShot shootingStrategy(ShootingStrategy strategy) {
		this.shootingStrategy = strategy;
		return this;
	}

	/**
	 * Takes the screenshot of given elements
	 * If elements were not found screenshot of whole page will be returned
	 *
	 * @param driver WebDriver instance
	 * @param elements Web elements to screenshot
	 * @return Screenshot with cropped image and list of ignored areas on screenshot
	 * @throws RuntimeException when something goes wrong
	 * @see Screenshot
	 */
	/*
	 >>> AShotMock#takeScreenshot elements.size()=1
	 >>> AShotMock#takeScreenshot cds.size()=1
	 >>> cd.toString()={"x":125,"y":4877,"width":64,"height":64}
	 */
	public Screenshot takeScreenshot(WebDriver driver, Collection<WebElement> elements) {

		println ">>> AShotMock#takeScreenshot elements.size()=${elements.size()}"

		Set<Coords> elementCoords = coordsProvider.ofElements(driver, elements);
		BufferedImage shot = shootingStrategy.getScreenshot(driver, elementCoords);

		ImageIO.write(shot, "PNG", new File("./tmp/raw.png"))

		Set<Coords> cds = shootingStrategy.prepareCoords(elementCoords)
		println ">>> AShotMock#takeScreenshot cds.size()=${cds.size()}"
		for (Coords cd: cds) {
			println ">>> cd.toString()=${cd.toString()}"
		}


		Screenshot screenshot = cropper.crop(shot, cds);
		ImageIO.write(screenshot.getImage(), "PNG", new File("./tmp/cropped.png"))




		Set<Coords> ignoredAreas = compileIgnoredAreas(driver, intersectingWith(screenshot));
		println ">>> AShotMock#takeScreenshot ignoredAreas.size()=${ignoredAreas.size()}"
		for (Coords ignoredArea: ignoredAreas) {
			println ">>> ignoredArea.toString()=${ignoredArea.toString()}"
		}
		screenshot.setIgnoredAreas(shootingStrategy.prepareCoords(ignoredAreas));
		return screenshot;
	}

	/**
	 * Takes the screenshot of given element
	 *
	 * @param driver WebDriver instance
	 * @param element Web element to screenshot
	 * @return Screenshot with cropped image and list of ignored areas on screenshot
	 * @throws RuntimeException when something goes wrong
	 * @see Screenshot
	 */
	public Screenshot takeScreenshot(WebDriver driver, WebElement element) {
		return takeScreenshot(driver, singletonList(element));
	}

	/**
	 * Takes the screenshot of whole page
	 *
	 * @param driver WebDriver instance
	 * @return Screenshot with whole page image and list of ignored areas on screenshot
	 * @see Screenshot
	 */
	public Screenshot takeScreenshot(WebDriver driver) {
		Screenshot screenshot = new Screenshot(shootingStrategy.getScreenshot(driver));
		screenshot.setIgnoredAreas(compileIgnoredAreas(driver, CoordsPreparationStrategy.simple()));
		return screenshot;
	}

	protected synchronized Set<Coords> compileIgnoredAreas(WebDriver driver, CoordsPreparationStrategy preparationStrategy) {
		Set<Coords> ignoredCoords = new HashSet<>();
		for (By ignoredLocator : ignoredLocators) {
			List<WebElement> ignoredElements = driver.findElements(ignoredLocator);
			if (!ignoredElements.isEmpty()) {
				ignoredCoords.addAll(preparationStrategy.prepare(coordsProvider.ofElements(driver, ignoredElements)));
			}
		}
		for (Coords ignoredArea : ignoredAreas) {
			ignoredCoords.addAll(preparationStrategy.prepare(singletonList(ignoredArea)));
		}
		return ignoredCoords;
	}

	@SuppressWarnings("UnusedDeclaration")
	public synchronized Set<By> getIgnoredLocators() {
		return ignoredLocators;
	}
}
