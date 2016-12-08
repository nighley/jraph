package com.jraph.svg.utils;

import java.awt.Color;

public class SvgColorFactory {
	
	private float h, s, b, step;
	
	public SvgColorFactory() {
		this(0f,0.7f,0.94f, 0.618033988f);
	}
	
	public SvgColorFactory(float h, float s, float b, float step) {
		this.h = h;
		this.s = s;
		this.b = b;
		this.step = step;
	}
	
	public SvgColor getNext() {
		int aColorInt = Color.HSBtoRGB(h, s, b);
		SvgColor aColor = new SvgColor(aColorInt);
		h += step;
		h %= 1f;
		return aColor;
	}
}
