package com.exmek.commons.utils;

public class MathUtils {

	private MathUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <N extends Number> N max(N a, N b) {
    	if (a == null || b == null) {
    		throw new IllegalArgumentException("Illegal argument of null is passed to call min() method.");
    	}
    	if (a instanceof Comparable ca && b instanceof Comparable cb) {
    		return ca.compareTo(cb) >= 0 ? a : b;
    	} else {
    		return a.doubleValue() - b.doubleValue() >= 0 ? a : b;
    	}
    }

	@SuppressWarnings("unchecked")
	public static <N extends Number> N min(N a, N b) {
    	if (a == null || b == null) {
    		throw new IllegalArgumentException("Illegal argument of null is passed to call min() method.");
    	}
    	if (a instanceof Comparable ca && b instanceof Comparable cb) {
    		return ca.compareTo(cb) <= 0 ? a : b;
    	} else {
    		return a.doubleValue() - b.doubleValue() <= 0 ? a : b;
    	}
    }
}
