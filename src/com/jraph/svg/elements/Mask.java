package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;

public class Mask extends Element{
	private List<Element> elements = new ArrayList<Element>();
	
	public Mask() {
		super("mask");
		setId(UUID.randomUUID().toString());
	}
	
	@Override
	protected void initParameters() {
		super.initParameters();
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : elements) {
			aBuilder.append(anElement.toSvg());
		}
		setContent(aBuilder.toString());
		removeParameter(Attribute.FILL);
	}

	public void addChildren(Element theElement) {
		elements.add(theElement);
	}

}
