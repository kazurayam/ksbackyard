package com.kazurayam.ksbackyard.test.ashot.cropper

import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.cropper.ImageCropper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;

import static ru.yandex.qatools.ashot.coordinates.Coords.setReferenceCoords;

/**
 * @author <a href="pazone@yandex-team.ru">Pavel Zorin</a>
 */

public class DefaultCropper_mock extends ImageCropper {

	@Override
	public Screenshot cropScreenshot(BufferedImage image, Set<Coords> coordsToCompare) {
		Coords cropArea = Coords.unity(coordsToCompare);

		Coords imageIntersection = Coords.ofImage(image).intersection(cropArea);

		println ">>> DefaultCropper_mock#cropScreenthsot imageIntersection is ${imageIntersection.toString()}"
		
		if (imageIntersection.isEmpty()) {
			println ">>> DefaultCropper_mock#cropScreenshot imageIntersecion is empty!"
			return new Screenshot(image);
		}

		BufferedImage cropped = new BufferedImage(imageIntersection.width, imageIntersection.height, image.getType());
		Graphics g = cropped.getGraphics();
		g.drawImage(
				image,
				0, 0,
				imageIntersection.width, imageIntersection.height,
				cropArea.x, cropArea.y,
				cropArea.x + imageIntersection.width, cropArea.y + imageIntersection.height,
				null
				);
		g.dispose();
		Screenshot screenshot = new Screenshot(cropped);
		screenshot.setOriginShift(cropArea);
		screenshot.setCoordsToCompare(setReferenceCoords(screenshot.getOriginShift(), coordsToCompare));
		return screenshot;
	}

	protected Coords createCropArea(Set<Coords> coordsToCompare) {
		return Coords.unity(coordsToCompare);
	}
}
