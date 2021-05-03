package me.hobrin.imageeditor.util;

public enum EnumDirection {
	NORTH(0, -1),
	SOUTH(0, 1),
	EAST(1, 0),
	WEST(-1, 0);
	
	final int offX;
	final int offY;
	private EnumDirection(int offX, int offY) {
		this.offX = offX;
		this.offY = offY;
	}
	public int offX() {
		return this.offX;
	}
	public int offY() {
		return this.offY;
	}
}
