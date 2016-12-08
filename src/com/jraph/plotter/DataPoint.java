package com.jraph.plotter;

public class DataPoint {
	private double x;
	private double y;
	private double ex;
	private double ey;

	public DataPoint(double x, double y) {
		this(x, y, 0, 0);
	}

	public DataPoint(double x, double y, double ex, double ey) {
		this.x = x;
		this.y = y;
		this.ex = ex;
		this.ey = ey;
	}

	@Override
	public DataPoint clone() {
		return new DataPoint(x, y, ex, ey);
	}
	
	public void addY(DataPoint thePoint) {
		y += thePoint.getY();
		ey = Math.sqrt(ey * ey + thePoint.getEy() * thePoint.getEy());
	}
	
	public DataPoint scaleY(double theFactor) {
		y *= theFactor;
		ey *= theFactor;
		return this;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getEx() {
		return ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}

	public double getEy() {
		return ey;
	}

	public void setEy(double ey) {
		this.ey = ey;
	}

	@Override
	public String toString() {
		return x + "+-" + ex + " / " + y + "+-" + ey;
	}

}
