package com.jraph.svg.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;

public class Pattern extends Element {
	private String patternUnits = "userSpaceOnUse";
	private int patternRot = 45;
	
	private List<Element> children = new ArrayList<>();
	
	public Pattern() {
		super("pattern");
		setId(UUID.randomUUID().toString());
	}
	
	@Override
	protected void initParameters() {
		super.initParameters();
		addParameter(new Parameter<String>(Attribute.PATTERNUNITS, patternUnits));
		addParameter(new Parameter<String>(Attribute.PATTERNTRANSFORM, "rotate("+patternRot+")"));
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : children) {
			aBuilder.append(anElement.toSvg());
		}
		setContent(aBuilder.toString());
	}
	
	public void addChildren(Element theElement) {
		children.add(theElement);
	}

	public int getPatternRot() {
		return patternRot;
	}

	public void setPatternRot(int patternRot) {
		this.patternRot = patternRot;
	}
	
	
	
}
