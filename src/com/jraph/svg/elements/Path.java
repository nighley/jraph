package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;

public class Path extends Element {
	private List<Vector2<Double>> path = new ArrayList<>();
	private boolean fill = false;
	private boolean close = false;
	
	public Path() {
		super("path");
		
	}
	
	protected void initParameters() {
		super.initParameters();
		StringBuilder aBuilder = new StringBuilder();
		for (Vector2<Double> aVec : path) {
			if (aBuilder.length() == 0) {
				aBuilder.append("M"+aVec.getX()+" "+aVec.getY()+" ");
			}
			aBuilder.append("L"+aVec.getX()+" "+aVec.getY()+" ");
		}
		if (close) {
			aBuilder.append("Z");
		}
		addParameter(new Parameter<String>(Attribute.D, aBuilder.toString()));
		if (!fill) {
			addParameter(new Parameter<String>(Attribute.FILL, "none"));
		}
	}

	public void setClose(boolean close) {
		this.close = close;
	}
	
	public void addPoint(Vector2<Double> aVector) {
		path.add(aVector);
	}
	
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
	public List<Vector2<Double>> getList() {
		return path;
	}
	

}
