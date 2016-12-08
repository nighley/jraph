package com.jraph.svg.utils;

public class Vector3<T extends Number> {
	private T x;
	private T y;
	private T z;

	public Vector3(Vector3<T> theCopy) {
		x = theCopy.x;
		y = theCopy.y;
		z = theCopy.z;
	}

	public Vector3(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3<T> add(Vector3<T> theOther) {
		x = NumberUtil.add(x, theOther.x);
		y = NumberUtil.add(y, theOther.y);
		z = NumberUtil.add(z, theOther.z);
		return this;
	}

	public T getX() {
		return x;
	}

	public T getY() {
		return y;
	}

	public T getZ() {
		return z;
	}

	public void setX(T theX) {
		x = theX;
	}

	public void setY(T theY) {
		y = theY;
	}

	public void setZ(T theZ) {
		z = theZ;
	}

	public void set(Vector3<T> vec) {
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}

	public void set(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return x + ", " + y + "," + z;
	}

}
