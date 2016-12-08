package com.jraph.plotter.renderer.label;

import java.util.ArrayList;
import java.util.List;

import com.jraph.svg.Element;
import com.jraph.svg.elements.Text;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;
import com.jraph.svg.utils.Vector3;

public class LabelRenderer {
	private static final LabelRenderer _instance = new LabelRenderer();

	public static LabelRenderer getInstance() {
		return _instance;
	}

	public List<Element> render(String theText, int theTextSize, Vector2<Double> thePosition) {
		return render(theText, theTextSize, thePosition, new Vector3<>(0.0, 0.0, 0.0));
	}

	public List<Element> render(String theText, int theTextSize, Vector2<Double> thePosition, SvgColor theColor) {
		return render(theText, theTextSize, thePosition, theColor, new Vector3<>(0.0, 0.0, 0.0));
	}

	public List<Element> render(String theText, Vector2<Double> thePosition) {
		return render(theText, thePosition, new Vector3<>(0.0, 0.0, 0.0));
	}

	public List<Element> render(String theText, int theTextSize, Vector2<Double> thePosition,
			Vector3<Double> theRotation) {
		List<Element> aList = new ArrayList<>();
		Text aTextElement = new Text();
		aTextElement.setText(theText);
		aTextElement.setTextSize(theTextSize);
		aTextElement.getPosition().set(thePosition);
		aTextElement.setRotation(theRotation);
		aList.add(aTextElement);
		return aList;
	}

	public List<Element> render(String theText, int theTextSize, Vector2<Double> thePosition, SvgColor color,
			Vector3<Double> theRotation) {
		List<Element> aList = new ArrayList<>();
		Text aTextElement = new Text();
		aTextElement.setText(theText);
		aTextElement.setFill(color);
		aTextElement.setTextSize(theTextSize);
		aTextElement.getPosition().set(thePosition);
		aTextElement.setRotation(theRotation);
		aList.add(aTextElement);
		return aList;
	}

	public List<Element> render(String theText, Vector2<Double> thePosition, Vector3<Double> theRotation) {
		List<Element> aList = new ArrayList<>();
		Text aTextElement = new Text();
		aTextElement.setText(theText);
		aTextElement.getPosition().set(thePosition);
		aTextElement.setRotation(theRotation);
		aList.add(aTextElement);
		return aList;
	}
}
