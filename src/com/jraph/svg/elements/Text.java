package com.jraph.svg.elements;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;
import com.jraph.svg.utils.DominantBaseline;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.TextAnchor;
import com.jraph.svg.utils.Vector2;
import com.jraph.svg.utils.Vector3;

public class Text extends Element {
	private String text;
	private Vector3<Double> rotation;
	private TextAnchor textAnchor = TextAnchor.MIDDLE;
	private DominantBaseline baseline = DominantBaseline.CENTRAL;
	private double textSize = 12;

	public Text() {
		super("text");
		text = "";
		setPosition(new Vector2<>(0.0,0.0));
		rotation = new Vector3<>(0.0,0.0,0.0);
		setFill(SvgColor.BLACK);
	}

	@Override
	protected void initParameters() {
		super.initParameters();
//		addParameter(new Parameter<>(Attribute.DOMINANT_BASELINE, baseline.toString()));
//		addParameter(new Parameter<>(Attribute.ALIGNMENT_BASELINE, "central"));
		addParameter(new Parameter<>(Attribute.TEXT_ANCHOR, textAnchor.toString()));
		addParameter(new Parameter<>(Attribute.TRANSFORM, "rotate("+rotation.toString()+")"));
		addParameter(new Parameter<>(Attribute.FONT_SIZE, textSize));
		addParameter(new Parameter<>(Attribute.FONT_FAMILY, "serif"));
		addParameter(new Parameter<>(Attribute.DY, textSize*0.35));
		setContent(text);
	}

	public void setText(String theText) {
		text = theText;
	}

	public TextAnchor getTextAnchor() {
		return textAnchor;
	}

	public void setTextAnchor(TextAnchor textAnchor) {
		this.textAnchor = textAnchor;
	}

	public DominantBaseline getBaseline() {
		return baseline;
	}

	public void setBaseline(DominantBaseline baseline) {
		this.baseline = baseline;
	}

	public String getText() {
		return text;
	}


	public void setRotation(Vector3<Double> theRotation) {
		rotation.set(theRotation);
	}

	public Vector3<Double> getRotation() {
		return rotation;
	}

	public void setTextSize(double theSize) {
		textSize = theSize;
	}

	public double getTextSize() {
		return textSize;
	}

}
