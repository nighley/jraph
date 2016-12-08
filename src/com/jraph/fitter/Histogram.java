package com.jraph.fitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.Histo;
import com.jraph.plotter.props.Range;

public class Histogram {
	private double[] histo;
	private double[] error;
	private long[] histoEntries;
	private int binX, binY;
	private Range xRange, yRange;
	private long entries;

	public Histogram(int theBinX, Range theX) {
		this(theBinX, theX, 1, new Range(0, 1));
	}

	public Histogram(int theBinX, Range theX, int theBinY, Range theY) {
		binX = theBinX;
		binY = theBinY;
		histo = new double[binX * binY];
		error = new double[binX * binY];
		histoEntries = new long[binX * binY];
		xRange = new Range(theX);
		yRange = new Range(theY);
	}

	public Histogram abs() {
		for (int q = 0; q < histo.length; q++) {
			histo[q] = Math.abs(histo[q]);
		}
		return this;
	}

	@Override
	public Histogram clone() {
		Histogram aHist = this.cloneEmpty();
		System.arraycopy(histo, 0, aHist.histo, 0, histo.length);
		System.arraycopy(error, 0, aHist.error, 0, error.length);
		System.arraycopy(histoEntries, 0, aHist.histoEntries, 0, histoEntries.length);
		return aHist;
	}

	public Histogram recut(Range newXRange, Range newYRange) {
		int startX = (int) (xRange.getPercentageFromValue(newXRange.getMin()) * binX);
		int endX = (int) (xRange.getPercentageFromValue(newXRange.getMax()) * binX);
		int startY = (int) (yRange.getPercentageFromValue(newYRange.getMin()) * binY);
		int endY = (int) (yRange.getPercentageFromValue(newYRange.getMax()) * binY);
		Histogram s = new Histogram(endX - startX, newXRange, endY - startY, newYRange);
		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				s.histo[(x - startX) + (y - startY) * s.binX] = this.histo[x + y * this.binX];
			}
		}
		return s;
	}

	public void removeLinearBins(int from, int to) {
		double[] histoOld = new double[binX];
		double[] histoErrorOld = new double[binX];
		long[] histoEntriesOld = new long[binX];
		System.arraycopy(histo, 0, histoOld, 0, binX);
		System.arraycopy(error, 0, histoErrorOld, 0, binX);
		System.arraycopy(histoEntries, 0, histoEntriesOld, 0, binX);
		histo = new double[binX - (to - from)];
		error = new double[binX - (to - from)];
		histoEntries = new long[binX - (to - from)];
		int c = 0;
		for (int q = 0; q < binX; q++) {
			if (q >= from && q < to) {
				continue;
			}
			histo[c] = histoOld[q];
			error[c] = histoErrorOld[q];
			histoEntries[c] = histoEntriesOld[q];
			c++;
		}
		binX = histo.length;
	}

	public Histogram cloneEmpty() {
		Histogram aGram = new Histogram(binX, xRange, binY, yRange);
		return aGram;
	}

	public void setBinContent(int x, int y, double v) {
		histo[x + y * binX] = v;
	}

	public double getBinContent(int x) {
		return histo[x];
	}

	public double getBinContent(int x, int y) {
		return histo[x + y * binX];
	}

	public double getBinCenter(int x) {
		return xRange.getValueFromMax(x, binX);
	}

	public void setBinContent(int x, double v) {
		setBinContent(x, 0, v);
	}

	public void setBinError(int x, int y, double e) {
		error[x + y * binX] = e;
	}

	public void setBinError(int x, double e) {
		setBinError(x, 0, e);
	}

	public double getBinError(int x, int y) {
		return error[x + y * binX];
	}

	public void rebin(int x) {
		if (binY > 1) {
			throw new RuntimeException("NO  YIN");
		}
		double[] histoOld = new double[binX];
		double[] histoErrorOld = new double[binX];
		long[] histoEntriesOld = new long[binX];
		System.arraycopy(histo, 0, histoOld, 0, binX);
		System.arraycopy(error, 0, histoErrorOld, 0, binX);
		System.arraycopy(histoEntries, 0, histoEntriesOld, 0, binX);
		histo = new double[binX / x];
		error = new double[binX / x];
		histoEntries = new long[binX / x];
		for (int q = 0; q < binX; q++) {
			histo[q / x] += histoOld[q];
			error[q / x] += histoErrorOld[q];
			histoEntries[q / x] += histoEntriesOld[q];
		}
		binX = binX / x;
	}

	public void rebinX(int x) {
		double[] histoOld = new double[binX * binY];
		double[] histoErrorOld = new double[binX * binY];
		long[] histoEntriesOld = new long[binX * binY];
		System.arraycopy(histo, 0, histoOld, 0, binX * binY);
		System.arraycopy(error, 0, histoErrorOld, 0, binX * binY);
		System.arraycopy(histoEntries, 0, histoEntriesOld, 0, binX * binY);
		histo = new double[(binX / x) * binY];
		error = new double[(binX / x) * binY];
		histoEntries = new long[(binX / x) * binY];
		for (int r = 0; r < binY; r++) {
			for (int q = 0; q < binX; q++) {
				histo[(q / x) + r * (binX / x)] += histoOld[q + r * binX];
				error[(q / x) + r * (binX / x)] += histoErrorOld[q + r * binX];
				histoEntries[(q / x) + r * (binX / x)] += histoEntriesOld[q + r * binX];
			}
		}
		binX = binX / x;
	}

	public void rebinY(int y) {
		double[] histoOld = new double[binX * binY];
		double[] histoErrorOld = new double[binX * binY];
		long[] histoEntriesOld = new long[binX * binY];
		System.arraycopy(histo, 0, histoOld, 0, binX * binY);
		System.arraycopy(error, 0, histoErrorOld, 0, binX * binY);
		System.arraycopy(histoEntries, 0, histoEntriesOld, 0, binX * binY);
		histo = new double[binX * (binY / y)];
		error = new double[binX * (binY / y)];
		histoEntries = new long[binX * (binY / y)];
		for (int r = 0; r < binY; r++) {
			for (int q = 0; q < binX; q++) {
				histo[q + (r / y) * binX] += histoOld[q + r * binX];
				error[q + (r / y) * binX] += histoErrorOld[q + r * binX];
				histoEntries[q + (r / y) * binX] += histoEntriesOld[q + r * binX];
			}
		}
		binY /= y;
	}

	public void fill(double x, double w) {
		entries++;
		int xBin = (int) (binX * xRange.getPercentageFromValue(x));
		if (xBin >= 0 && xBin < binX) {
			histo[xBin] += w;
			histoEntries[xBin] += 1;
		}
	}

	public void fill(double x, double y, double w) {
		entries++;
		int xBin = (int) (binX * xRange.getPercentageFromValue(x));
		int yBin = (int) (binY * yRange.getPercentageFromValue(y));
		if (xBin >= 0 && xBin < binX && yBin >= 0 && yBin < binY) {
			histo[xBin + yBin * binX] += w;
		}
	}

	public void clearNaN() {
		for (int q = 0; q < histo.length; q++) {
			histo[q] = histo[q] != histo[q] ? 0 : histo[q];
			if (Double.isInfinite(histo[q])) {
				histo[q] = 0;
			}
		}
	}

	public Histogram divide(Histogram theOther) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] /= theOther.histo[q];
		}
		return this;
	}

	public Histogram multiply(Histogram theOther) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] *= theOther.histo[q];
		}
		return this;
	}

	public double[] getHisto() {
		return histo;
	}

	public Histo createHisto() {
		return new Histo(binX, binY, xRange, yRange, getHisto());
	}

	public double[] getError() {
		return error;
	}

	public int getBinX() {
		return binX;
	}

	public int getBinY() {
		return binY;
	}

	public long getEntries() {
		return entries;
	}

	public double getMean() {
		double aMean = 0;
		for (int q = 0; q < histo.length; q++) {
			aMean += xRange.getValueFromPercentage((q + 0.5) / histo.length) * histo[q];
		}
		if (aMean == 0.0) {
			return 0.0;
		}
		aMean /= getArea();
		return aMean;
	}

	public double getArea() {
		return Arrays.stream(histo).map(i -> Math.abs(i) * xRange.getLength() / binX).sum();
	}

	public Range getXRange() {
		return xRange;
	}

	public Range getYRange() {
		return yRange;
	}

	public double getTrimmedMean(double p) {
		List<Double> aList = new ArrayList<>();
		for (int i = 0; i < histo.length; i++) {
			for (int q = 0; q < histoEntries[i]; q++) {
				aList.add(xRange.getValueFromPercentage((i + 0.5) / histo.length));
			}
		}
		aList.sort(Double::compare);
		int gap = (int) (aList.size() * (p)) / 2;
		for (int q = 0; q < gap; q++) {
			aList.remove(0);
			aList.remove(aList.size() - 1);
		}
		if (aList.size() == 0) {
			return 0.0;
		}
		double mean = 0;
		for (int q = 0; q < aList.size(); q++) {
			mean += aList.get(q);
		}
		mean /= aList.size();
		return mean;
	}

	@Override
	public String toString() {
		return Arrays.toString(histo);
	}

	public Data createData() {
		Data aData = new Data();
		List<DataPoint> aPointList = new ArrayList<>();
		for (int q = 0; q < binX; q++) {
			aPointList.add(new DataPoint(xRange.getValueFromMax(q, binX), histo[q], 0, error[q]));
		}
		aData.setPoints(aPointList);
		return aData;
	}

	public Histogram scale(double d) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] *= d;
			error[q] *= d;
		}
		return this;
	}

	public void setXRange(Range range) {
		xRange = new Range(range);
	}

	public double getValueAt(double v) {
		int xBin = (int) (binX * xRange.getPercentageFromValue(v));
		return getBinContent(xBin);
	}

	public double getMaximum() {
		return Arrays.stream(histo).mapToObj(i -> (Double) i).sorted((d1, d2) -> Double.compare(d2, d1)).findFirst()
				.get().doubleValue();
	}
	
	public double getMinimum() {
		return Arrays.stream(histo).mapToObj(i -> (Double) i).sorted((d1, d2) -> Double.compare(d1, d2)).findFirst()
				.get().doubleValue();
	}

	public void add(Histogram histogram) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] += histogram.histo[q];
			error[q] = Math.sqrt(Math.pow(histogram.error[q], 2) + Math.pow(error[q], 2));
			histoEntries[q] += histogram.histoEntries[q];
		}
		entries += histogram.entries;
	}
	
	public void add(Histogram histogram, DoubleBinaryOperator op) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] += histogram.histo[q];
			error[q] = op.applyAsDouble(histogram.error[q], error[q]);
			histoEntries[q] += histogram.histoEntries[q];
		}
		entries += histogram.entries;
	}

	public Histogram minus(Histogram histogram) {
		for (int q = 0; q < histo.length; q++) {
			histo[q] -= histogram.histo[q];
			error[q] = Math.sqrt(Math.pow(histogram.error[q], 2) + Math.pow(error[q], 2));
			histoEntries[q] += histogram.histoEntries[q];
		}
		entries += histogram.entries;
		return this;
	}

	public void clipZero() {
		for (int q = 0; q < histo.length; q++) {
			histo[q] = Math.max(0, histo[q]);
		}
	}

	public Histogram projectY(int fromBin, int toBin) {
		Histogram out = new Histogram(binY, yRange);
		for (int x = fromBin; x < toBin + 1; x++) {
			for (int y = 0; y < binY; y++) {
				out.histo[y] += this.histo[x + y * binX];
				out.error[y] += Math.pow(this.error[x + y * binX], 2);
			}
		}
		for (int y = 0; y < binY; y++) {
			out.error[y] = Math.sqrt(out.error[y]);
		}
		return out;
	}

	public Histogram square() {
		for (int q = 0; q < histo.length; q++) {
			histo[q] *= histo[q];
		}
		return this;
	}

	public Histogram sqrt() {
		for (int q = 0; q < histo.length; q++) {
			histo[q] = Math.sqrt(histo[q]);
		}
		return this;
	}

	public void scaleBin(int x, int y, double d) {
		setBinContent(x, y, getBinContent(x, y) * d);
		setBinError(x, y, getBinError(x, y) * d);
	}

	public void mirrorAxis() {
		double[] nhisto = new double[histo.length];
		double[] nerror = new double[error.length];
		long[] nhistoEntries = new long[histoEntries.length];

		for (int x = 0; x < binX; x++) {
			for (int y = 0; y < binY; y++) {
				nhisto[y + x * binY] = histo[x + y * binX];
				nerror[y + x * binY] = error[x + y * binX];
				nhistoEntries[y + x * binY] = histoEntries[x + y * binX];
			}
		}
		
		System.arraycopy(nhisto, 0, histo, 0, histo.length);
		System.arraycopy(nerror, 0, error, 0, histo.length);
		System.arraycopy(nhistoEntries, 0, histoEntries, 0, histo.length);

		Range tempRange = xRange;
		xRange = yRange;
		yRange = tempRange;

		int temp = binX;
		binX = binY;
		binY = temp;

	}

	public void addErrorWeighted(Histogram histogram) {
		DoubleBinaryOperator weighted = (d1, d2)->(d1/(d2*d2));
		for (int q = 0; q < histo.length; q++) {
			if (histo[q] == histogram.histo[q] && error[q] == histogram.error[q]) {
				System.out.println("skip duplicate");
				continue;
			}
			histo[q] = (weighted.applyAsDouble(histogram.histo[q], histogram.error[q]) + weighted.applyAsDouble(histo[q], error[q]))
					/ (weighted.applyAsDouble(1, histogram.error[q])+weighted.applyAsDouble(1, error[q]));
			error[q] = 1.0 / Math.sqrt(weighted.applyAsDouble(1, histogram.error[q])+weighted.applyAsDouble(1, error[q]));
			histoEntries[q] += histogram.histoEntries[q];
		}
		entries += histogram.entries;
	}

}
