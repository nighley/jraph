package com.jraph.plotter.props;

import com.jraph.svg.utils.Vector2;

public class Inset {
	private double top, left, right, bottom;

	public Inset() {
		this(0,0,0,0);
	}
	
	public Inset(double theTop, double theRight, double theBottom, double theLeft) {
		top = theTop;
		right = theRight;
		bottom = theBottom;
		left = theLeft;
	}

	public Inset(Vector2<Integer> size, Inset margin) {
		top = size.getX() * margin.top;
		bottom = size.getX() * margin.bottom;
		left = size.getY() * margin.left;
		right = size.getY() * margin.right;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public void set(int theTop, int theRight, int theBottom, int theLeft) {
		bottom = theBottom;
		left = theLeft;
		right = theRight;
		top = theTop;
	}
	
	@Override
	public String toString() {
		return "("+top+","+left+","+bottom+","+right+")";
	}

	public void set(Inset margin) {
		bottom = margin.bottom;
		left = margin.left;
		right = margin.right;
		top = margin.top;
	}

}
