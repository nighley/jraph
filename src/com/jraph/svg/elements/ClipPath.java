package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;

public class ClipPath extends Element {
	private List<Element> elements = new ArrayList<Element>();
	private String id;
	
	public ClipPath(String theId) {
		super("clipPath");
		id = theId;
	}

	@Override
	protected void initParameters() {
		addParameter(new Parameter<String>(Attribute.ID, id));
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : elements) {
			aBuilder.append(anElement.toSvg()+"\n");
		}
		setContent(aBuilder.toString());
	}
	
	public void addAll(List<Element> theList) {
		elements.addAll(theList);
	}
	
	public String getId() {
		return id;
	}
}
