package me.hobrin.imageeditor.function.refactor;

import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.function.IFunction;

public class FunctionFlipXY extends IFunction {

	public FunctionFlipXY() {
		super("FlipXY", "Flips the X and Y");
	}
	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		BufferedImage img = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				img.setRGB(x, y, image.getRGB(y, x));
			}
		}
		return img;
	}

	@Override
	public boolean activateOnHold() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean activateOnClick() {
		return false;
	}

}
