package com.jraph.svg.elements;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;
import com.jraph.svg.utils.NumberUtil;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.Vector2;

public class Line extends Element {
	private Vector2<Double> start;
	private Vector2<Double> end;
	
	public Line() {
		super("line");
		start = new Vector2<>(0.0,0.0);
		end = new Vector2<>(0.0,0.0);
		setStroke(new Stroke());
	}

	public Line(Vector2<Double> start, Vector2<Double> stop) {
		super("line");

		this.start = start;
		this.end = stop;
		setStroke(new Stroke());
	}

	private double round(double d) {
        return NumberUtil.round(d);
    }
	
	@Override
	protected void initParameters() {
		super.initParameters();
		addParameter(new Parameter<Double>(Attribute.X1, round(start.getX())));
		addParameter(new Parameter<Double>(Attribute.Y1, round(start.getY())));
		addParameter(new Parameter<Double>(Attribute.X2, round(end.getX())));
		addParameter(new Parameter<Double>(Attribute.Y2, round(end.getY())));
	}

	public Vector2<Double> getStart() {
		return start;
	}

	public void setStart(Vector2<Double> start) {
		this.start = start;
	}

	public Vector2<Double> getEnd() {
		return end;
	}

	public void setEnd(Vector2<Double> end) {
		this.end = end;
	}
}
