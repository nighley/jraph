package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.Element;

public class Defs extends Element {
	private List<Element> elements = new ArrayList<Element>();
	
	public Defs() {
		super("defs");
	}
	
	@Override
	protected void initParameters() {
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : elements) {
			aBuilder.append(anElement.toSvg());
		}
		setContent(aBuilder.toString());
	}
	
	public void addChildren(Element theElement) {
		elements.add(theElement);
	}
}
