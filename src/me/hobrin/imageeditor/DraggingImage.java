package me.hobrin.imageeditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class DraggingImage {
	public BufferedImage img;
	public int x;
	public int y;
	
	public DraggingImage(BufferedImage img, int x, int y) {
		this.img = img;
		this.x = x;
		this.y = y;
	}
	
	public Point getPointLoc() {
		return new Point(x, y);
	}
	
	public Rectangle rect() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
}
