package com.jraph.fitter;

import java.util.ArrayList;
import java.util.List;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.props.Range;

public class Function {
	private double[] parameters;
	private double[] parMin;
	private double[] parMax;
	private boolean[] fixed;

	private Formula formula;

	public Function(Formula theFormula, int params) {
		parameters = new double[params];
		parMin = new double[params];
		parMax = new double[params];
		fixed = new boolean[params];
		for (int q = 0; q < params; q++) {
			parMin[q] = -100;
			parMax[q] = 100;
			fixed[q] = false;
		}
		formula = theFormula;
	}

	public double eval(double x) {
		return formula.eval(x, parameters);
	}

	public double area(Range range, double de) {
		double area = 0;
		int steps = (int)(range.getLength()/de);
		for (int q = 0; q < steps; q++) {
			double h1 = eval(range.getValueFromPercentage((q/(double)steps)));
			double h2 = eval(range.getValueFromPercentage((q+1/(double)steps)));
			area += h1 < h2 ? h1*de + (h2-h1)*de/2.0 : h2*de + (h1-h2)*de/2.0;
		}
		return area;
	}

	public Formula getFormula() {
		return formula;
	}

	public double[] getParMax() {
		return parMax;
	}

	public double[] getParMin() {
		return parMin;
	}

	public double[] getParameters() {
		return parameters;
	}

	public void setParameters(double[] newParams) {
		System.arraycopy(newParams, 0, parameters, 0, parameters.length);
	}

	public int getParNum() {
		return parMin.length;
	}

	public boolean[] getFixed() {
		return fixed;
	}

	public void setFixed(boolean[] fixed) {
		this.fixed = fixed;
	}

	public void setParMax(double[] parMax) {
		this.parMax = parMax;
	}

	public void setParMin(double[] parMin) {
		this.parMin = parMin;
	}

	public Data createData(Range theRange) {
		double from = theRange.getMin();
		double to = theRange.getMax();
		Data aNewData = new Data();
		List<DataPoint> aList = new ArrayList<>();
		for (int q = 0; q < 100; q++) {
			double v = from + q * (to - from) / 99.0;
			aList.add(new DataPoint(v, eval(v)));
		}
		aNewData.setPoints(aList);
		return aNewData;
	}
}
