package com.jraph.plotter.components;

import com.jraph.svg.utils.SvgColor;

public class Label  {
	private String text;
	private double size;
	private SvgColor color = SvgColor.BLACK;

	public Label(String theText){
		text = theText;
		size = 12;
	}

	public Label(String theText, int theSize) {
		text = theText;
		size = theSize;
	}

	public Label(String theText, int theSize, SvgColor theColor) {
		text = theText;
		size = theSize;
		color = theColor;
	}

	public void setColor(SvgColor color) {
		this.color = color;
	}

	public SvgColor getColor() {
		return color;
	}

	public String getText() {
		String copy = text;
		copy = "<tspan>"+copy;
		copy += "</tspan>";
		while (copy.contains("_{")) {
			copy = copy.replaceFirst("_\\{", "</tspan><tspan dy=\"5\">");
			copy = copy.replaceFirst("\\}", "</tspan><tspan dy=\"-5\">");
		}
		while (copy.contains("^{")) {
			copy = copy.replaceFirst("\\^\\{", "</tspan><tspan dy=\"-5\">");
			copy = copy.replaceFirst("\\}", "</tspan><tspan dy=\"5\">");
		}
		return copy;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

}
