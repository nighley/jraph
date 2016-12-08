package com.jraph.svg.utils;

import java.math.BigDecimal;

public class NumberUtil {
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T add(T x, T x2) {
		if (x instanceof Float) {
			return (T)(new Float(x.floatValue() + x2.floatValue()));
		} else if (x instanceof Double) {
			return (T)(new Double(x.doubleValue() + x2.doubleValue()));
		} else if (x instanceof Integer) {
			return (T)(new Integer(x.intValue() + x2.intValue()));
		}
		throw new RuntimeException("Not a number");
	}

	public static double round(double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
