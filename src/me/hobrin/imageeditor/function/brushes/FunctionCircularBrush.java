package me.hobrin.imageeditor.function.brushes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.function.inputs.ParamInt;

public class FunctionCircularBrush extends IFunctionBrush {

	public FunctionCircularBrush() {
		super("CircleBrush", "A normal Brush", new ParamInt("size", 15));
	}

	@Override
	protected BufferedImage apply(BufferedImage img, int x, int y, Color col) {
		Graphics2D g = (Graphics2D) img.getGraphics();
		int size = (Integer) this.params[0].getValue();
		int half = size/2;
		g.setColor(col);
		g.fillOval(x-half, y-half, size, size);
		return img;
	}

}
