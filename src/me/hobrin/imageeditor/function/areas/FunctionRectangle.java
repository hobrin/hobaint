package me.hobrin.imageeditor.function.areas;

import java.awt.image.BufferedImage;

public class FunctionRectangle extends FunctionArea {

	public FunctionRectangle() {
		super("Rectangle", "Fills a rectangle with a specific colour.");
	}

	@Override
	public boolean activateOnHold() {
		return true;
	}
	@Override
	public boolean hasActButton() {
		return true;
	}


	@Override
	public BufferedImage apply(BufferedImage area) {
		int col = getPrimaryColor().getRGB();
		
		for (int x = 0; x < area.getWidth(); x++) {
			for (int y = 0; y < area.getHeight(); y++) {
				area.setRGB(x, y, col);
			}
		}
		
		return area;
	}
	@Override
	public boolean activateOnClick() {
		return false;
	}

}
