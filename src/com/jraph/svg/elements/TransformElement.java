package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;
import com.jraph.svg.utils.Vector2;

public class TransformElement extends Element {

	private Vector2<Double> translation = new Vector2<Double>(0.0,0.0);
	
	private List<Element> elements = new ArrayList<Element>();
	
	public TransformElement() {
		super("g");
	}

	@Override
	protected void initParameters() {
		super.initParameters();
		addParameter(new Parameter<String>(Attribute.TRANSFORM, "translate("+translation.getX()+" "+translation.getY()+")"));
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : elements) {
			aBuilder.append(anElement.toSvg()+"\n");
		}
		setContent(aBuilder.toString());
	}

	public void addAll(List<Element> theList) {
		elements.addAll(theList);
	}

	public void setOffset(Vector2<Double> theOffset) {
		translation.set(theOffset);
	}

}
