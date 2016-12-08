package com.jraph.svg.elements;

import com.jraph.svg.Attribute;
import com.jraph.svg.Element;
import com.jraph.svg.Parameter;
import com.jraph.svg.utils.NumberUtil;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;

public class Circle extends Element {
    private Vector2<Double> center;
    private double radius;

    public Circle() {
        super("circle");

        this.center = new Vector2<Double>(0.0, 0.0);
        this.radius = 1;
    }

    public Circle(Vector2<Double> center, double radius) {
        super("circle");

        this.center = center;
        this.radius = radius;
    }

    private double round(double d) {
        return NumberUtil.round(d);
    }

    @Override
    protected void initParameters() {
    	super.initParameters();
        addParameter(new Parameter<Double>(Attribute.CX, round(center.getX())));
        addParameter(new Parameter<Double>(Attribute.CY, round(center.getY())));
        addParameter(new Parameter<Double>(Attribute.R, round(radius)));
    }

    public Vector2<Double> getCenter() {
        return center;
    }

    public void setCenter(Vector2<Double> center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

  
}
