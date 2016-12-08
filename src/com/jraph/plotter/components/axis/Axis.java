package com.jraph.plotter.components.axis;

import java.math.BigDecimal;

import com.jraph.plotter.components.Pad;
import com.jraph.plotter.props.Range;
import com.jraph.svg.utils.Stroke;

public class Axis {
	private AxisStyle axisStyle = AxisStyle.INWARD;

	private Pad pad;
	private Range range;
	private AxisOrientation axisOrientation;

	private int tickSize = 20;
	
	private BigDecimal majorTickUnit;
	private int subTicks;
	private Stroke stroke;
	
	private boolean ignoreHighest = false;

	private boolean renderLabels = false;

	private int tickFontSize = 10;
	
	private int shift = 0;
	
	private boolean log = false;

	public Axis(Pad thePad, Range theRange, AxisOrientation theAxisOrientation) {

		pad = thePad;
		range = theRange;
		axisOrientation = theAxisOrientation;
		majorTickUnit = new BigDecimal((theRange.getMax() - theRange.getMin())/5); // 5 Ticks
																		// as
																		// standard
		subTicks = 3;
		stroke = new Stroke();
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}

	public BigDecimal getMajorTickUnit() {
		return majorTickUnit;
	}

	public void setMajorTickUnit(BigDecimal majorTickUnit) {
		this.majorTickUnit = majorTickUnit;
	}

	public int getSubTicks() {
		return subTicks;
	}

	public void setSubTicks(int subTicks) {
		this.subTicks = subTicks;
	}

	public AxisStyle getAxisStyle() {
		return axisStyle;
	}

	public void setAxisStyle(AxisStyle theStyle) {
		axisStyle = theStyle;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke theStroke) {
		stroke = theStroke;
	}

	public Pad getPad() {
		return pad;
	}

	public AxisOrientation getAxisOrientation() {
		return axisOrientation;
	}

	public boolean isRenderLabels() {
		return renderLabels;
	}

	public void setRenderLabels(boolean renderLabels) {
		this.renderLabels = renderLabels;
	}

	public void setTickFontSize(int fontSize) {
		tickFontSize = fontSize;
	}
	
	public int getTickFontSize() {
		return tickFontSize ;
	}

	public boolean isIgnoreHighest() {
		return ignoreHighest;
	}

	public void setIgnoreHighest(boolean ignoreHighest) {
		this.ignoreHighest = ignoreHighest;
	}
	
	public void setShift(int shift) {
		this.shift = shift;
	}
	
	public int getShift() {
		return shift;
	}

	public void setTickSize(int i) {
		tickSize = i;
	}
	
	public int getTickSize() {
		return tickSize;
	}

	public void setLog(boolean b) {
		log = true;
	}
	
	public boolean isLog() {
		return log;
	}
	

}
