package com.jraph.svg;

public enum Attribute {
	XMLNS,
	ID,
	D,
	X,
	Y,
	DY,
	DX,
	X1,
	Y1,
	X2,
	Y2,
	STROKE_WIDTH,
	STROKE,
	WIDTH,
	HEIGHT,
	CX,
	CY,
	R,
	FILL,
	TEXT_ANCHOR,
	DOMINANT_BASELINE,
	FONT_SIZE,
	TRANSFORM,
	CLIP_PATH,
	MASK,
	FILL_OPACITY,
	STROKE_DASHARRAY, PATTERNUNITS("patternUnits"), PATTERNTRANSFORM("patternTransform"), ALIGNMENT_BASELINE, FONT_FAMILY;
	
	
	private String realTag = null;
	
	private Attribute() {
		realTag = null;
	}
	
	private Attribute(String realTag) {
		this.realTag = realTag;
	}
	
	public String getRealTag() {
		return realTag;
	}
}
