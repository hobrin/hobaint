package me.hobrin.imageeditor.util;

import java.awt.Point;
import java.awt.Rectangle;

import me.hobrin.imageeditor.function.inputs.IParam;

public class GeometryUtil {
	public static Rectangle getRectArea(Point a, Point b) {
		Point[] points = points(a, b);
		a = points[0];
		b = points[1];
		return new Rectangle(a.x, a.y, b.x - a.x, b.y - a.y);
	}
	
	public static Point[] points(Rectangle rect) {
		int x1 = rect.x;
		int y1 = rect.y;
		int x2 = rect.width + rect.x;
		int y2 = rect.height + rect.y;
		
		int left;
		int right;
		if (x1<x2) {
			left = x1;
			right = x2;
		} else {
			left = x2;
			right = x1;
		}
		
		int top;
		int bottom;
		if (y1<y2) {
			top = y1;
			bottom = y2;
		} else {
			top = y2;
			bottom = y1;
		}
		return new Point[] {new Point(left, top), new Point(right, bottom)};
	}
	public static Point[] points(Point a, Point b) {
		int x1 = a.x;
		int y1 = a.y;
		int x2 = b.x;
		int y2 = b.y;
		
		int left;
		int right;
		if (x1<x2) {
			left = x1;
			right = x2;
		} else {
			left = x2;
			right = x1;
		}
		
		int top;
		int bottom;
		if (y1<y2) {
			top = y1;
			bottom = y2;
		} else {
			top = y2;
			bottom = y1;
		}
		return new Point[] {new Point(left, top), new Point(right, bottom)};
	}
	public static Point[] points(int start, IParam<?>... params) {
		int x1 = (Integer) params[start+0].getValue();
		int y1 = (Integer) params[start+1].getValue();
		int x2 = (Integer) params[start+2].getValue();
		int y2 = (Integer) params[start+3].getValue();
		
		int left;
		int right;
		if (x1<x2) {
			left = x1;
			right = x2;
		} else {
			left = x2;
			right = x1;
		}
		
		int top;
		int bottom;
		if (y1<y2) {
			top = y1;
			bottom = y2;
		} else {
			top = y2;
			bottom = y1;
		}
		return new Point[] {new Point(left, top), new Point(right, bottom)};
	}
}
