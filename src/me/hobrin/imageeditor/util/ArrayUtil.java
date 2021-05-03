package me.hobrin.imageeditor.util;

import me.hobrin.imageeditor.function.inputs.IParam;

public class ArrayUtil {
	public static IParam<?>[] mendParams(IParam<?>[] a, IParam<?>... b) {
		int aLen = a.length;
        int bLen = b.length;
        
        IParam<?>[] result = new IParam<?>[aLen + bLen];

        System.arraycopy(a, 0, result, 0, aLen);
        System.arraycopy(b, 0, result, aLen, bLen);
		
        return result;
	}
}
