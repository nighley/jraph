package com.jraph.svg.elements;

import com.jraph.svg.Element;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.Vector2;

public class Rect extends Element {

	public Rect() {
		super("rect");
		setPosition(new Vector2<Double>(0.0, 0.0));
		setDimension(new Vector2<Double>(1.0, 1.0));
	}

	public Rect(Vector2<Double> pos, Vector2<Double> dim) {
		super("rect");

		setPosition(pos);
		setDimension(dim);
		setStroke(new Stroke());
	}

	@Override
	protected void initParameters() {
		super.initParameters();
		
	}
	
	@Override
	public String toString() {
		return getPosition()+" "+getDimension();
	}
}
