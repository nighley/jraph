package com.jraph.plotter.components.axis;

import com.jraph.plotter.components.Label;
import com.jraph.plotter.components.Pad;
import com.jraph.svg.utils.Vector2;

public class AxisLabel extends Label {
	private Pad pad;
	private AxisOrientation axisOrientation;
	private Vector2<Double> offset = new Vector2<Double>(0.0, 0.0);

	public AxisLabel(String theText, Pad thePad, AxisOrientation theAxisOrientation) {
		super(theText);
		setSize(12);
		pad = thePad;
		setText(theText);
		axisOrientation = theAxisOrientation;
	}

	public Pad getPad() {
		return pad;
	}

	public AxisOrientation getAxisOrientation() {
		return axisOrientation;
	}

	public Vector2<Double> getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = new Vector2<>(0.0, offset);
	}

	public void setOffset(Vector2<Double> offset) {
		this.offset = new Vector2<Double>(offset);
	}
}
