package me.hobrin.imageeditor.function.areas;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.ArrayList;
import java.util.List;

import me.hobrin.imageeditor.EditingImage;
import me.hobrin.imageeditor.function.IFunction;
import me.hobrin.imageeditor.function.inputs.IParam;
import me.hobrin.imageeditor.function.inputs.ParamInt;
import me.hobrin.imageeditor.util.ArrayUtil;
import me.hobrin.imageeditor.util.GeometryUtil;

public abstract class FunctionArea extends IFunction {
	
	private boolean dragging = false;
	private Rectangle selection = null;
	private final int start;
	
	public FunctionArea(String name, String descr, IParam<?>... params) {
		super(name, descr, 
				ArrayUtil.mendParams(params, new ParamInt("X1"), new ParamInt("Y1"), new ParamInt("X2"), new ParamInt("Y2"))
		);
		start = params.length;
	}
	public List<Point> getPointsInArea() {
		List<Point> points = new ArrayList<>();
		
		Point topLeft = this.topLeft();
		Point bottomRight = this.bottomRight();
		for (int x = topLeft.x; x < bottomRight.x; x++) {
			for (int y = topLeft.y; y < bottomRight.y; y++) {
				points.add(new Point(x, y));
			}
		}
		
		return points;
	}
	
	@Override
	public BufferedImage apply(BufferedImage image, EditingImage editing) {
		this.dragging = editing.viewer.dragging;
		this.selection = editing.selection;
		BufferedImage areaIn;
		try {
			areaIn = this.getAreaImage(image);
		} catch (RasterFormatException exc) {
			System.out.println("Invalid square area, 0 size.");
			return null;
		}
		BufferedImage areaOut = this.apply(areaIn);
		
		if (areaOut == null)
			areaOut = areaIn;
		
		Point topLeft = this.topLeft();
		
		for (int x = 0; x < areaOut.getWidth(); x++) {
			for (int y = 0; y < areaOut.getHeight(); y++) {
				image.setRGB(topLeft.x+x, topLeft.y+y, areaOut.getRGB(x, y));
			}
		}
		
		return image;
	}
	public abstract BufferedImage apply(BufferedImage area);
	
	public BufferedImage getAreaImage(BufferedImage img) {
		Rectangle rect = this.getRectArea();
		return img.getSubimage(rect.x, rect.y, rect.width, rect.height);
	}
	public Point topLeft() {
		return this.points()[0];
	}
	public Point bottomRight() {
		return this.points()[1];
	}
	public Rectangle getRectArea() {
		Point[] points = this.points();
		return GeometryUtil.getRectArea(points[0], points[1]);
	}
	//returns 2 points.
	public Point[] points() {
		if (selection == null) {
			return GeometryUtil.points(this.start, params);
		} else {
			return GeometryUtil.points(this.selection);
		}
	}
	
}
