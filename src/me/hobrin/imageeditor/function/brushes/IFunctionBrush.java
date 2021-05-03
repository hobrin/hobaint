package me.hobrin.imageeditor.function.brushes;

import java.awt.Color;
import java.awt.image.BufferedImage;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.Viewer;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.IParam;

public abstract class IFunctionBrush extends IFunction{

	public IFunctionBrush(String name, String descr, IParam<?>... params) {
		super(name, descr, params);
	}

	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		Viewer viewer = editing.viewer;
		return this.apply(image, viewer.mouseX, viewer.mouseY, viewer.prospecT.getPrimaryColor());
	}
	
	protected abstract BufferedImage apply(BufferedImage img, int x, int y, Color col);
	
	@Override
	public boolean activateOnHold() {
		return true;
	}
	@Override
	public boolean hasUnsavedBuffer() {
		return true;
	}
	@Override
	public boolean shouldShowSelection() {
		return false;
	}
	@Override
	public boolean activateOnClick() {
		return true;
	}
}
