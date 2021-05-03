package me.hobrin.imageeditor.function.areas;

import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.ImageProcessing;
import me.hobrin.imageeditor.function.inputs.ParamBoolean;

public class FunctionFlip extends FunctionArea {

	public FunctionFlip() {
		super("Flip", "Flips a specific area horizontally or/and vertically.", new ParamBoolean("Horizontal", true), new ParamBoolean("Vertical", true));
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
		boolean flip = (Boolean) this.params[0].getValue();
		boolean flop = (Boolean) this.params[1].getValue();
		
		return ImageProcessing.flipflop(area, flip, flop);
	}
	@Override
	public boolean activateOnClick() {
		return false;
	}
}
