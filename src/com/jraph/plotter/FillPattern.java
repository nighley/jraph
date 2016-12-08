package com.jraph.plotter;

import com.jraph.svg.utils.SvgColor;

public class FillPattern {
	private int rot;
	private SvgColor fill = SvgColor.BLACK;
	
	public FillPattern(int rot) {
		this.rot = rot;
	}
	
	public int getRot() {
		return rot;
	}
	
	public void setRot(int rot) {
		this.rot = rot;
	}
	
	public SvgColor getFill() {
		return fill;
	}

	public void setColor(SvgColor theFill) {
		fill = theFill;
	}
	
	public SvgColor getColor() {
		return fill;
	}
}
