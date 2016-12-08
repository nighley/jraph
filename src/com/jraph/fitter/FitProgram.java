package com.jraph.fitter;

import java.util.Arrays;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.props.Range;

public class FitProgram {
	public static int FIT_NUM_STEP = 10;

	private Function function;
	private Histogram histogram;
	private Data data;

	private double[] currentPar;
	private double[] minPar;
	private double[] maxPar;

	public FitProgram(Function theFunction, Histogram theHistogram) {
		function = theFunction;
		histogram = theHistogram;
	}

	public FitProgram(Function theFunction, Data theData) {
		function = theFunction;
		data = theData;
	}

	public void fit() {
		fit(new Range(-1e99, 1e99));
	}

	public void fit(Range range) {
		currentPar = new double[function.getParNum()];
		minPar = new double[function.getParNum()];
		maxPar = new double[function.getParNum()];

		System.arraycopy(function.getParameters(), 0, currentPar, 0, function.getParNum());
		System.arraycopy(function.getParMin(), 0, minPar, 0, function.getParNum());
		System.arraycopy(function.getParMax(), 0, maxPar, 0, function.getParNum());

		double totalScore = err(currentPar, range);
		double oldScore = 2e99;

		int iterations = 0;
		while (Math.abs((totalScore - oldScore) / 2.0) > totalScore * 0.00001) {
			oldScore = totalScore;
			for (int q = 0; q < function.getParNum(); q++) {
				if (function.getFixed()[q]) {
					continue;
				}
				double parScore = err(currentPar, range);
				double parOldScore = 1e99;
				double parStep = Math.abs(maxPar[q] - minPar[q]) / 1000.0;
				int sig = 1;
				int smallIter = 0;
				while (Math.abs((parScore - parOldScore) / 2.0) > totalScore * 0.00001) {
					parOldScore = parScore;
					double newPar = clamp(currentPar[q] + parStep * sig, minPar[q], maxPar[q]);
					// System.out.println(currentPar[q] + " -> " + newPar + " ("
					// + parStep + ")" + " should be "
					// + (currentPar[q] + parStep * sig));
					currentPar[q] = newPar;
					parScore = err(currentPar, range);
					if (parScore > parOldScore) {
						sig *= -1;
						parStep /= 2.0;
					}
					smallIter++;
				}
			}
			iterations++;
			totalScore = err(currentPar, range);
		}
		function.setParameters(currentPar);

	}

	private double clamp(double val, double min, double max) {
		return Math.min(max, Math.max(min, val));
	}

	public Function getFunction() {
		return function;
	}

	public Histogram getHistogram() {
		return histogram;
	}

	public Data getData() {
		return data;
	}

	public double err(double[] params, Range range) {
		double error = 0;
		if (histogram != null) {
			for (int x = 0; x < histogram.getBinX(); x++) {
				double binValue = histogram.getBinContent(x);
				double binCenter = histogram.getBinCenter(x);
				if (range.contains(binCenter)) {
					double fitValue = function.getFormula().eval(binCenter, params);
					error += Math.pow(binValue - fitValue, 2);
				}
			}
			error /= (double) histogram.getBinX();
		} else if (data != null) {
			for (DataPoint aPoint : data.getPoints()) {
				double binValue = aPoint.getY();
				double binCenter = aPoint.getX();
				if (range.contains(binCenter)) {
					double fitValue = function.getFormula().eval(binCenter, params);
					error += Math.pow(binValue - fitValue, 2);
				}
			}
			error /= (double) data.getPoints().size();
		}
		return error;
	}

}
