package com.jraph.plotter.helper;

import com.jraph.plotter.props.Range;

public abstract class CoordsTransformer {
	private boolean log;
	
	private Range coords;
	private Range pixel;

	public CoordsTransformer(Range coords, Range pixel, boolean theLog) {
		this.log = theLog;
		this.coords = coords;
		this.pixel = pixel;
	}

	public double distanceCoordsToPixel(double theLength) {
		return theLength / coords.getLength() * pixel.getLength();
	}
	// height*log(y-min+1)/log(max-min+1)
	public double coordsToPixel(double c) {
		return !log ? pixel.getMin()
				+ (pixel.getMax() - pixel.getMin()) * (c - coords.getMin()) / (coords.getMax() - coords.getMin())
				: pixel.getMin()
				+ (pixel.getMax() - pixel.getMin()) * (Math.log10(c - coords.getMin() + 1) / (Math.log10(coords.getMax() - coords.getMin() + 1)));
	}

	public double pixelToCoords(double p) {
		return coords.getMin()
				+ (coords.getMax() - coords.getMin()) * (p - pixel.getMin()) / (pixel.getMax() - pixel.getMin());
	}

	public abstract double coordsToScreenPixel(double c);

	public abstract double screenPixelToCoords(double p);

	public Range getCoordsRange() {
		return coords;
	}

	public Range getPixelRange() {
		return pixel;
	}
}
