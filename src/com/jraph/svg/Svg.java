package com.jraph.svg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.utils.Vector2;

public class Svg extends Element {
	private Vector2<Integer> dimension;
	
	private List<Element> elements;
	
	public Svg(Vector2<Integer> theSize) {
		super("svg");
		dimension = new Vector2<>(theSize);
		elements = new ArrayList<>();
	}
	
	public void addElement(Element theElement) {
		elements.add(theElement);
	}

	public void addAllElements(List<Element> list) {
        elements.addAll(list);
    }
	
	@Override
	protected void initParameters() {
		addParameter(new Parameter<String>(Attribute.XMLNS, "http://www.w3.org/2000/svg"));
		addParameter(new Parameter<Integer>(Attribute.WIDTH, dimension.getX()));
		addParameter(new Parameter<Integer>(Attribute.HEIGHT, dimension.getY()));
		StringBuilder aBuilder = new StringBuilder();
		for (Element anElement : elements) {
			aBuilder.append(anElement.toSvg()).append("\n");
		}
		setContent(aBuilder.toString());
	}
	
	public void save(String theFileName) {
		File aFile = new File(theFileName);
		try {
			FileWriter aFileWriter = new FileWriter(aFile);
			aFileWriter.append(toSvg());
			aFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
