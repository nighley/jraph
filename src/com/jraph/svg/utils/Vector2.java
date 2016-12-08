package com.jraph.svg.utils;

public class Vector2<T extends Number> {
	private T x;
	private T y;

	public Vector2(Vector2<T> theCopy) {
		x = theCopy.x;
		y = theCopy.y;
	}
	
	public Vector2(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2<T> add(Vector2<T> theOther) {
		x = NumberUtil.add(x, theOther.x);
		y = NumberUtil.add(y, theOther.y);
		return this;
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}

	public void setX(T theX) {
		x = theX;
	}

	public void setY(T theY) {
		y = theY;
	}

	public void set(Vector2<T> vec) {
		x = vec.x;
		y = vec.y;
	}
	
	public void set(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}

}
