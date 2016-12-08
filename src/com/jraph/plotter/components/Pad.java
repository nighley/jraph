package com.jraph.plotter.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.Histo;
import com.jraph.plotter.components.axis.Axis;
import com.jraph.plotter.components.axis.AxisLabel;
import com.jraph.plotter.components.axis.AxisOrientation;
import com.jraph.plotter.helper.XCoordsTransformer;
import com.jraph.plotter.helper.YCoordsTransformer;
import com.jraph.plotter.props.Inset;
import com.jraph.plotter.props.Range;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.Vector2;

public class Pad {
	private String id = UUID.randomUUID().toString();

	private Label title;
	private Vector2<Double> titleOffset;

	private final Vector2<Double> offset; // TODO: The offset is missing in all
	// the renders
	private final Vector2<Integer> dimension;
	private AxisLabel xLabel, yLabel, xLabel2, yLabel2;
	private Inset margin;
	private Axis yAxisLeft, xAxisBottom, yAxisRight, xAxisTop;

	private List<Data> datas, datas2;

	private Histo histo;

	private Vector2<Double> keyPosition = new Vector2<>(0.0, 0.0);
	private String keyTitle = null;
	private int keyTitleSize = 15;
	private int keyEntrySize = 12;

	private Stroke zeroLineStroke;

	private Map<Label, Vector2<Double>> labels = new HashMap<>();

	public Pad(Vector2<Integer> theDimension) {
		datas = new ArrayList<>();
		offset = new Vector2<>(0.0, 0.0);
		dimension = new Vector2<>(theDimension);
		margin = new Inset(30, 30, 30, 30);
		yAxisLeft = new Axis(this, new Range(0, 1), AxisOrientation.VERTICAL_LEFT);
		xAxisBottom = new Axis(this, new Range(0, 1), AxisOrientation.HORIZONTAL_BOTTOM);
		yAxisRight = new Axis(this, new Range(0, 1), AxisOrientation.VERTICAL_RIGHT);
		xAxisTop = new Axis(this, new Range(0, 1), AxisOrientation.HORIZONTAL_TOP);
		xLabel = new AxisLabel("X-Axis", this, AxisOrientation.HORIZONTAL_BOTTOM);
		yLabel = new AxisLabel("Y-Axis", this, AxisOrientation.VERTICAL_LEFT);
	}

	public AxisLabel getXLabel() {
		return xLabel;
	}

	public void setXLabel(AxisLabel xLabel) {
		this.xLabel = xLabel;
	}

	public AxisLabel getYLabel() {
		return yLabel;
	}

	public void setYLabel(AxisLabel yLabel) {
		this.yLabel = yLabel;
	}

	public Inset getMargin() {
		return margin;
	}

	public void setMargin(Inset margin) {
		this.margin = margin;
	}

	public Axis getYAxisLeft() {
		return yAxisLeft;
	}

	public Axis getXAxisBottom() {
		return xAxisBottom;
	}

	public Axis getYAxisRight() {
		return yAxisRight;
	}

	public Axis getXAxisTop() {
		return xAxisTop;
	}

	public Vector2<Double> getOffset() {
		return offset;
	}

	public Vector2<Integer> getDimension() {
		return dimension;
	}

	public int getKeyTitleSize() {
		return keyTitleSize;
	}

	public void setKeyTitleSize(int keyTitleSize) {
		this.keyTitleSize = keyTitleSize;
	}

	public int getKeyEntrySize() {
		return keyEntrySize;
	}

	public void setKeyEntrySize(int keyEntrySize) {
		this.keyEntrySize = keyEntrySize;
	}

	public XCoordsTransformer getXCoordTransformer() {
		int xLeft = (int) (0 + margin.getLeft());
		int xRight = (int) (dimension.getX() - margin.getRight());
		return new XCoordsTransformer(getXAxisBottom().getRange(), new Range(xLeft, xRight), false);
	}

	public YCoordsTransformer getYCoordTransformer() {
		int yTop = (int) (0 + margin.getTop());
		int yBottom = (int) (dimension.getY() - margin.getBottom());
		return new YCoordsTransformer(getYAxisLeft().getRange(), new Range(yTop, yBottom), getYAxisLeft().isLog());
	}

	public Vector2<Double> getPixelFromCoord(double x, double y) {
		return new Vector2<>(getXCoordTransformer().coordsToScreenPixel(x),
				getYCoordTransformer().coordsToScreenPixel(y));
	}

	public void addData(Data aData) {
		datas.add(aData);
	}

	public List<Data> getDatas() {
		return datas;
	}

	public void setBothXAxis(Range theRange) {
		xAxisBottom.setRange(theRange);
		xAxisTop.setRange(theRange);
	}

	public void setBothYAxis(Range theRange) {
		yAxisLeft.setRange(theRange);
		yAxisRight.setRange(theRange);
	}

	public void setBothXAxisMajorUnit(BigDecimal theUnitSize) {
		xAxisBottom.setMajorTickUnit(theUnitSize);
		xAxisTop.setMajorTickUnit(theUnitSize);
	}

	public void setBothYAxisMajorUnit(BigDecimal theUnitSize) {
		yAxisLeft.setMajorTickUnit(theUnitSize);
		yAxisRight.setMajorTickUnit(theUnitSize);
	}

	public String getKeyTitle() {
		return keyTitle;
	}

	public void setKeyTitle(String keyTitle) {
		this.keyTitle = keyTitle;
	}

	public void setXAxisBottom(Axis theAxis) {
		xAxisBottom = theAxis;
	}

	public void setYAxisLeft(Axis theAxis) {
		yAxisLeft = theAxis;
	}

	public void setXAxisTop(Axis theAxis) {
		xAxisTop = theAxis;
	}

	public void setYAxisRight(Axis theAxis) {
		yAxisRight = theAxis;
	}

	public String getId() {
		return id;
	}

	public void setZeroLineStroke(Stroke theZeroLineStroke) {
		zeroLineStroke = theZeroLineStroke;
	}

	public Stroke getZeroLineStroke() {
		return zeroLineStroke;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	public void setTitleOffset(Vector2<Double> theOffset) {
		titleOffset = theOffset;
	}

	public Vector2<Double> getTitleOffset() {
		return titleOffset;
	}

	public void autoRange() {
		autoRange(1.1);
	}

	public void setBothYAxisMinorTicks(int i) {
		getYAxisLeft().setSubTicks(i);
		getYAxisRight().setSubTicks(i);
	}

	public Vector2<Double> getKeyPosition() {
		return keyPosition;
	}

	public void setKeyPosition(Vector2<Double> keyPosition) {
		this.keyPosition = keyPosition;
	}

	public void setHisto(Histo theHisto) {
		histo = theHisto;
	}

	public Histo getHisto() {
		return histo;
	}

	public void autoRange(double d) {
		double xMin = Double.MAX_VALUE;
		double yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		double yMax = Double.MIN_VALUE;
		if (histo != null) {
			xMin = histo.getxRange().getMin();
			xMax = histo.getxRange().getMax();
			yMin = histo.getyRange().getMin();
			yMax = histo.getyRange().getMax();
		} else {

			for (Data aData : datas) {
				for (DataPoint aDataPoint : aData.getPoints()) {
					xMin = Math.min(xMin, aDataPoint.getX() - aDataPoint.getEx());
					yMin = Math.min(yMin, aDataPoint.getY() - aDataPoint.getEy());
					xMax = Math.max(xMax, aDataPoint.getX() + aDataPoint.getEx());
					yMax = Math.max(yMax, aDataPoint.getY() + aDataPoint.getEy());
				}
			}
		}

		yMax *= d;

		if (yMin == yMax) {
			yMax += 1;
		}
		if (xMin == xMax) {
			xMax += 1;
		}

		getXAxisBottom().setRange(new Range(xMin, xMax));
		getXAxisTop().setRange(new Range(xMin, xMax));
		getYAxisLeft().setRange(new Range(yMin, yMax));
		getYAxisRight().setRange(new Range(yMin, yMax));
		BigDecimal aDec = new BigDecimal((getXAxisBottom().getRange().getLength() / 3.0));
		if (Math.abs(aDec.doubleValue()) > 10) {
			aDec = aDec.setScale(0, RoundingMode.HALF_UP);
		}
		setBothXAxisMajorUnit(aDec);

		aDec = new BigDecimal((getYAxisLeft().getRange().getLength() / 4.0));
		if (Math.abs(aDec.doubleValue()) > 10) {
			aDec = aDec.setScale(0, RoundingMode.HALF_UP);
		}
		setBothYAxisMajorUnit(aDec);
	}

	public void addLabel(Label theLabel, Vector2<Double> thePosition) {
		labels.put(theLabel, thePosition);
	}

	public Map<Label, Vector2<Double>> getLabels() {
		return labels;
	}
}
