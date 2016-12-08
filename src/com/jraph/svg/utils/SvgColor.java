package com.jraph.svg.utils;

import java.awt.Color;

public class SvgColor {
	public static SvgColor BLACK = new SvgColor(0, 0, 0);
	public static SvgColor LIGHT_GREY = new SvgColor(200, 200, 200);
	public static SvgColor DARK_GREY = new SvgColor(100, 100, 100);
	public static SvgColor BLUE = new SvgColor(0x0000FF), CRIMSON = new SvgColor(0xDC143C),
			GREEN = new SvgColor(0x008000), BRIGHT_GREEN = new SvgColor(0x00FF00),
			YELLOW_GREEN = new SvgColor(0x9ACD32);
	public static SvgColor RED = new SvgColor(0xFF0000);
	public static SvgColor ORANGE = new SvgColor(0xFF9E00);
	public static SvgColor BRIGHT_ORANGE = new SvgColor(0xFFC973);
	public static SvgColor DARK_ORANGE = new SvgColor(0xFF8C00);
	public static SvgColor CORAL = new SvgColor(0xFF7F50);
	public static SvgColor LIGHT_CORAL = new SvgColor(0xF08080);
	public static SvgColor OLIVE = new SvgColor(0x808000);
	public static SvgColor WHITE = new SvgColor(0xffffff);
	public static SvgColor DARK_PINK = new SvgColor(0x7A005C);
	public static SvgColor PINK = new SvgColor(0xDD37B4);
	public static SvgColor LIGHT_BLUE = new SvgColor(124, 124, 172);
	public static SvgColor DARK_GREEN = new SvgColor(0x006400);

	private int r, g, b;
	private double a;

	public SvgColor(float hue) {
		Color aColor = new Color(Color.HSBtoRGB(hue, 1.0f, 1.0f));
		this.r = aColor.getRed();
		this.g = aColor.getGreen();
		this.b = aColor.getBlue();
		a = 1;
	}

	public SvgColor(int color) {
		Color aColor = new Color(color);
		r = aColor.getRed();
		b = aColor.getBlue();
		g = aColor.getGreen();
		a = 1;
	}

	public SvgColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "rgb(" + r + "," + g + "," + b + ")";
	}

	public String toRGB() {
		return "rgb(" + r + "," + g + "," + b + ")";
	}

	public String toRGBLine() {
		return "rgb(" + r + "," + g + "," + b + ")\" stroke-opacity=\"" + a;
	}

	public String toRGBFill() {
		return "rgb(" + r + "," + g + "," + b + ")\" fill-opacity=\"" + a;
	}
}
