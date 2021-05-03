package me.hobrin.imageeditor.function.refactor;

import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.ImageProcessing;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.ParamBoolean;

public class FunctionRotate extends IFunction {

	public FunctionRotate() {
		super("Rotate", "Rotates your image by 90 degrees or -90", new ParamBoolean("isRight", false));
	}
	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		BufferedImage img = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				img.setRGB(x, y, image.getRGB(y, x));
			}
		}
		boolean flip = (Boolean) this.params[0].getValue();
		boolean flop = !flip;
		return ImageProcessing.flipflop(img, flip, flop);
	}

	@Override
	public boolean activateOnHold() {
		return false;
	}
	@Override
	public boolean activateOnClick() {
		return false;
	}

}
