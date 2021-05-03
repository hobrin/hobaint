package me.hobrin.imageeditor.function.replace;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.ParamDouble;
import me.hobrin.imageeditor.util.ColorUtil;
import me.hobrin.imageeditor.util.EnumDirection;

public class FunctionReplace extends IFunction {

	public FunctionReplace() {
		super("Replace", "Replaces a clicked colour with another colour.", new ParamDouble("Allowed Similarity", 50D));
	}

	@Override
	public BufferedImage apply(BufferedImage img, EditingImage editing) {
		Point cursor = new Point(editing.viewer.mouseX, editing.viewer.mouseY);
		
		double allowedSim = (double) this.params[0].getValue();
		int replacedCol = editing.viewer.prospecT.getPrimaryColor().getRGB();
		int col = img.getRGB(cursor.x, cursor.y);
		
		if (ColorUtil.isSimilar(replacedCol, col, allowedSim)) return null; //no edits are needed, is nescessary to save computer.
		
		//we use the stack instead, to avoid a stackoverflow error.
		Stack<Point> stack = new Stack<Point>();
		stack.push(cursor);
		while (!stack.isEmpty()) {
			try {
				Point point = stack.pop();
				int x = point.x, y = point.y;
				//col = img.getRGB(x, y);
				img.setRGB(x, y, replacedCol);
				for (EnumDirection dir : EnumDirection.values()) {
					if (ColorUtil.isSimilar(col, img.getRGB(x + dir.offX(), y + dir.offY()), allowedSim)) {
						stack.push(new Point(x + dir.offX(), y + dir.offY()));
					}
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				
			}
		}
		return null;
	}

	@Override
	public boolean activateOnHold() {
		return false;
	}

	@Override
	public boolean activateOnClick() {
		return true;
	}

}
