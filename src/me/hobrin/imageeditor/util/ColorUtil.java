package me.hobrin.imageeditor.util;


public class ColorUtil {
	public static int getRed( int col ) {
 		return ((col>>16)&0xFF);
    }
	public static int getGreen( int col ) {
		return ((col>>8)&0xFF);
	}
    public static int getBlue( int col ) {
		return (col & 0xff);
	}
    public static boolean isSimilar(int colA, int colB) {
		return isSimilar(colA, colB, 30D);
	}
    public static boolean isSimilar(int colA, int colB, double weight) {
		return getSimilarity(colA, colB) < weight;
	}
    
    public static double getSimilarity(int colA, int colB) {		
		int r = ColorUtil.getRed(colA) - ColorUtil.getRed(colB);
		int g = ColorUtil.getGreen(colA) - ColorUtil.getGreen(colB);
		int b = ColorUtil.getBlue(colA) - ColorUtil.getBlue(colB);
		double off = Math.sqrt(r*r + g*g + b*b);
		return off;
    }
}
