package me.hobrin.imageeditor.function.refactor;

import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.ParamDouble;

public class FunctionScale extends IFunction {
	public FunctionScale() {
		super("Scale", "Resizes the image to different proportions", new ParamDouble("X", 1D), new ParamDouble("Y", 1D));
	}

	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		double scaleX = (Double) this.params[0].getValue();
		double scaleY = (Double) this.params[1].getValue();
		
		BufferedImage img = new BufferedImage((int) ((double) image.getWidth()*scaleX), (int) ((double) image.getHeight()*scaleY), image.getType());
		
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int oldX = (int) (1D/scaleX * (double) x);
				int oldY = (int) (1D/scaleY * (double) y);
				img.setRGB(x, y, image.getRGB(oldX, oldY));
			}
		}
		
		return img;
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
