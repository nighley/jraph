package com.jraph.svg.utils;

public class Stroke {
	private SvgColor color;
	private double width;
	private boolean visible;
	private int[] strokeDashArray = null;
	
	public Stroke() {
		width = 1.0;
		color = new SvgColor(0,0,0);
        visible = true;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public SvgColor getColor() {
		return color;
	}

	public void setColor(SvgColor color) {
		this.color = color;
	}

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void setStrokeDashArray(int[] theDashArray) {
    	strokeDashArray = theDashArray;
    }
    
    public int[] getStrokeDashArray() {
    	return strokeDashArray;
    }
}
