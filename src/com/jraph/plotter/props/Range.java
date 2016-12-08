package com.jraph.plotter.props;

public class Range {
	private double min;
	private double max;

	public Range(double theMin, double theMax) {
		min = theMin;
		max = theMax;
	}
	
	public Range(Range theRange) {
		min = theRange.min;
		max = theRange.max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}
	
	public double getLength() {
		return Math.abs(min-max);
	}

	public void setMax(double max) {
		this.max = max;
	}

	public boolean contains(double theValue) {
		return theValue >= min && theValue <= max;
	}
	
	public double getPercentageFromValue(double theValue) {
		theValue -= min;
		theValue /= (max - min);
		return theValue;
	}
	
	public double getValueFromPercentage(double thePercentage) {
		thePercentage *= (max - min);
		thePercentage += min;
		return thePercentage;
	}
	
	public double getValueFromMax(int bin, int maxBins) {
		return getValueFromPercentage((bin+0.5)/(double)maxBins);
	}
	
	@Override
	public String toString() {
		return "["+min+","+max+"]";
	}

	public void pad(double d) {
		min *= (1-d);
		max *= (1+d);
	}
}
