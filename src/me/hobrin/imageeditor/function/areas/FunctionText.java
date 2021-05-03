package me.hobrin.imageeditor.function.areas;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.ParamDouble;
import me.hobrin.imageeditor.function.inputs.ParamString;

public class FunctionText extends IFunction {

	public FunctionText() {
		super("Text", "Writes text on your image.", new ParamDouble("Size", 4D), new ParamString("Text", "abc"));
	}

	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		double scale = (Double) this.params[0].getValue();
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(this.getPrimaryColor());
		g.scale(scale, scale);
		g.drawString((String) this.params[1].getValue(), (int) (editing.viewer.mouseX / scale), (int) (editing.viewer.mouseY / scale));
		
		return image;
	}

	@Override
	public boolean activateOnHold() {
		return false;
	}
	@Override
	public boolean activateOnClick() {
		return true;
	}
	@Override
	public boolean shouldShowSelection() {
		return false;
	}
}
