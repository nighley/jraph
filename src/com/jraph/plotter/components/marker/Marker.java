package com.jraph.plotter.components.marker;

import com.jraph.svg.utils.SvgColor;

public class Marker {
	private SvgColor color;
	private SvgColor fill;
	private SvgColor errorColor = SvgColor.BLACK;
	private boolean error;
	private MarkerStyle style;
	private double weight = 1;
	private double errorWeight = 1;
	
	private double size;
	
	public Marker() {
		color = new SvgColor(0, 0, 0);
		fill = null;
		style = MarkerStyle.X;
		size = 10;
	}

	public Marker(MarkerStyle style) {
        color = new SvgColor(0, 0, 0);
        this.style = style;
        size = 10;
    }

	public SvgColor getColor() {
		return color;
	}

	public void setColor(SvgColor color) {
		this.color = color;
	}

	public MarkerStyle getStyle() {
		return style;
	}

	public void setStyle(MarkerStyle style) {
		this.style = style;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public SvgColor getFill() {
		return fill;
	}
	
	public void setFill(SvgColor theFill) {
		fill = theFill;
	}

	public void setError(boolean b) {
		error = b;
	}
	
	public boolean getError() {
		return error;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public SvgColor getErrorColor() {
		return errorColor;
	}
	
	public void setErrorColor(SvgColor errorColor) {
		this.errorColor = errorColor;
	}
	
	public double getErrorWeight() {
		return errorWeight;
	}
	
	public void setErrorWeight(double markerWeight) {
		this.errorWeight = markerWeight;
	}
}
