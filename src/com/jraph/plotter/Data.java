package com.jraph.plotter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jraph.plotter.components.marker.Marker;
import com.jraph.plotter.props.Range;
import com.jraph.plotter.renderer.BasicDataRenderer;
import com.jraph.plotter.renderer.DataRenderer;
import com.jraph.svg.utils.Stroke;

public class Data {
	private String name;
	private DataRenderer renderer = BasicDataRenderer.getInstance();
	private Marker marker;
	private Stroke line;
	private Stroke histoLine;
	private double baseline = 0;
	private boolean invisible = false;
	
	private FillPattern fillPattern;

	private List<DataPoint> points;

	public Data() {
		points = new ArrayList<DataPoint>();
		marker = new Marker();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setRenderer(DataRenderer renderer) {
		this.renderer = renderer;
	}

	public List<DataPoint> getPoints() {
		return points;
	}

	public void setPoints(List<DataPoint> points) {
		this.points = points;
	}
	
	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
        this.marker = marker;
    }
	
	public DataRenderer getRenderer() {
		return renderer;
	}

	public Stroke getLine() {
		return line;
	}

	public void setLine(Stroke line) {
		this.line = line;
	}
	
	public void setHistoLine(Stroke theLine) {
		histoLine = theLine;
	}
	
	public Stroke getHistoLine() {
		return histoLine;
	}
	
	public void setPattern(FillPattern pat) {
		fillPattern = pat;
	}
	
	public FillPattern getFillPattern() {
		return fillPattern;
	}

	public double integral() {
		double integral = 0;
		for (DataPoint aPoint : points) {
			integral += aPoint.getY();
		}
		return integral;
	}

	public Range getMinMax() {
		double min = points.stream().map(d->d.getY()).sorted().findFirst().get();
		double max = points.stream().map(d->d.getY()).sorted((d1,d2)->Double.compare(d2, d1)).findFirst().get();
		return new Range(min, max);
	}
	
	public void setBaseline(double baseline) {
		this.baseline = baseline;
	}
	
	public double getBaseline() {
		return baseline;
	}
	
	
	public boolean isInvisible() {
		return invisible;
	}
	
	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}
}
