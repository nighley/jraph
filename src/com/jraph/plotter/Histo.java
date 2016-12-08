package com.jraph.plotter;

import java.util.Arrays;

import com.jraph.plotter.components.Label;
import com.jraph.plotter.props.Range;
import com.jraph.plotter.renderer.BasicHistoRenderer;
import com.jraph.plotter.renderer.HistoRenderer;

public class Histo {
	private HistoRenderer renderer = BasicHistoRenderer.getInstance();

	private int nBinsX;
	private int nBinsY;

	private Range xRange;
	private Range yRange;
	private Range zRange;

	private boolean log = false;

	private boolean renderKey = false;
	private double scale = 10;

	private double[] list;
	
	private Label zAxisLabel;

	private double zLabelOffset;

	private boolean zIgnoreHighest;
	
	private double alphaRange = 40.0;

	public Histo(int x, int y, Range rX, Range rY, double[] list) {
		nBinsX = x;
		nBinsY = y;
		xRange = rX;
		yRange = rY;
		this.list = list;
	}

	public double getMaximum() {
		return Arrays.stream(list).max().getAsDouble();
	}

	public int getnBinsX() {
		return nBinsX;
	}

	public void setnBinsX(int nBinsX) {
		this.nBinsX = nBinsX;
	}

	public int getnBinsY() {
		return nBinsY;
	}

	public void setnBinsY(int nBinsY) {
		this.nBinsY = nBinsY;
	}

	public Range getxRange() {
		return xRange;
	}

	public void setxRange(Range xRange) {
		this.xRange = xRange;
	}

	public Range getyRange() {
		return yRange;
	}

	public void setyRange(Range yRange) {
		this.yRange = yRange;
	}

	public double[] getList() {
		return list;
	}

	public HistoRenderer getRenderer() {
		return renderer;
	}

	public Range getzRange() {
		return zRange;
	}

	public void setzRange(Range zRange) {
		this.zRange = zRange;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public boolean isLog() {
		return log;
	}

	public boolean isRenderKey() {
		return renderKey;
	}

	public void setRenderKey(boolean renderKey) {
		this.renderKey = renderKey;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setzAxisLabel(Label zAxisName) {
		this.zAxisLabel = zAxisName;
	}
	
	public Label getzAxisLabel() {
		return zAxisLabel;
	}

	public double getzLabelOffset() {
		return zLabelOffset;
	}
	
	public void setzLabelOffset(double zLabelOffset) {
		this.zLabelOffset = zLabelOffset;
	}

	public void setzIgnoreHighest(boolean b) {
		zIgnoreHighest = b;
	}
	
	public boolean iszIgnoreHighest() {
		return zIgnoreHighest;
	}
	
	public double getAlphaRange() {
		return alphaRange;
	}
	
	public void setAlphaRange(double alphaRange) {
		this.alphaRange = alphaRange;
	}
	
}
