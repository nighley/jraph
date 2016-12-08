package com.jraph.svg;

public class Parameter<T> implements Comparable<Parameter<?>>{
	private final Attribute key;
	private final T value;
	
	public Parameter(Attribute theKey, T theValue) {
		key = theKey;
		value = theValue;
	}

	public Attribute getKey() {
		return key;
	}

	public T getValue() {
		return value;
	}
	
	@Override
	public int compareTo(Parameter<?> arg0) {
		if (arg0 == null) {
			return -1;
		}
		return Integer.compare(getKey().ordinal(), arg0.getKey().ordinal());
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Parameter<?>)) {
			return false;
		}
		return getKey().equals(((Parameter<?>)obj).getKey());
	}
}
