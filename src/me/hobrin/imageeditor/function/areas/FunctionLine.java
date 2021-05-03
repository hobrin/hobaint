package me.hobrin.imageeditor.function.areas;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.Viewer;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.ParamDouble;

public class FunctionLine extends IFunction {

	public FunctionLine() {
		super("Line", "Draws a line in a specific colour.", new ParamDouble("Width", 3D));
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
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		Viewer v = editing.viewer;
		
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(getPrimaryColor());
		double width = (Double) this.params[0].getValue();
        g.setStroke(new BasicStroke((float) width));
		g.drawLine(v.a.x, v.a.y, v.b.x, v.b.y);
		
		return image;
	}
	
	@Override
	public boolean shouldShowSelection() {
		return false;
	}
	@Override
	public boolean activateOnClick() {
		return false;
	}

}
