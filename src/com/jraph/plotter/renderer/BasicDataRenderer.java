package com.jraph.plotter.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.components.Pad;
import com.jraph.plotter.components.marker.Marker;
import com.jraph.plotter.helper.XCoordsTransformer;
import com.jraph.plotter.helper.YCoordsTransformer;
import com.jraph.svg.Element;
import com.jraph.svg.elements.Circle;
import com.jraph.svg.elements.Defs;
import com.jraph.svg.elements.Line;
import com.jraph.svg.elements.Mask;
import com.jraph.svg.elements.Path;
import com.jraph.svg.elements.Pattern;
import com.jraph.svg.elements.Rect;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;

public class BasicDataRenderer extends DataRenderer {

	public static final BasicDataRenderer _instance = new BasicDataRenderer();

	public static BasicDataRenderer getInstance() {
		return _instance;
	}

	private BasicDataRenderer() {

	}

	@Override
	public List<Element> render(Pad thePad, Data theData) {
		if (theData.isInvisible()) {
			return Collections.emptyList();
		}
		List<DataPoint> aPointList = theData.getPoints();
		List<Element> aList = new ArrayList<Element>();
		XCoordsTransformer anXTrans = thePad.getXCoordTransformer();
		YCoordsTransformer anYTrans = thePad.getYCoordTransformer();
		Path aPath = new Path();
		for (DataPoint aPoint : aPointList) {

			double x = anXTrans.coordsToScreenPixel(aPoint.getX());
			double y = anYTrans.coordsToScreenPixel(aPoint.getY());
			double ey = anYTrans.distanceCoordsToPixel(aPoint.getEy());
			double eyU = y - ey;
			double eyD = y + ey;

			aList.addAll(renderYErrorBar(theData.getMarker(), x, y, eyU, eyD));
			aList.addAll(renderMarker(theData.getMarker(), x, y, aPoint));
			aPath.addPoint(new Vector2<Double>(x, y));
		}

		if (theData.getLine() != null) {
			aPath.setStroke(theData.getLine());
			aList.add(aPath);
		}
		if (theData.getFillPattern() != null) {
			Path aFillPath = new Path();
			for (Vector2<Double> pos : aPath.getList()) {
				aFillPath.addPoint(pos);
			}
			double aZeroLine = anYTrans.coordsToScreenPixel(theData.getBaseline());
			List<Vector2<Double>> aVecList = aFillPath.getList();
			aVecList.add(0, new Vector2<Double>(aVecList.get(0).getX(), aZeroLine));
			aVecList.add(new Vector2<Double>(aVecList.get(aVecList.size() - 1).getX(), aZeroLine));
			aFillPath.setClose(true);
			aFillPath.setStroke(null);

			Defs aDefs = new Defs();

			Pattern aPattern = new Pattern();
			aPattern.setPatternRot(theData.getFillPattern().getRot());
			aPattern.setDimension(new Vector2<Double>(4.0, 4.0));

			Rect aRect = new Rect(null, new Vector2<Double>(2.0, 4.0)); // TODO
			aRect.setFill(SvgColor.WHITE);
			aRect.setStroke(null);
			aPattern.addChildren(aRect);
			aDefs.addChildren(aPattern);

			Mask aMask = new Mask();
			Rect aMaskRect = new Rect(new Vector2<Double>(0.0, 0.0), new Vector2<Double>(100.0, 100.0));
			aMaskRect.setStroke(null);
			aMaskRect.setDimInPercent(true);
			aMaskRect.setFillUrl(aPattern.getId());
			aMask.addChildren(aMaskRect);
			aDefs.addChildren(aMask);

			aList.add(aDefs);

			aFillPath.setMask(aMask.getId());
			aFillPath.setFill(theData.getFillPattern().getFill());
			aList.add(aFillPath);
		}

		
		if (theData.getHistoLine() != null) {
			
			Path aPath2 = new Path();
			aPath2.setStroke(theData.getHistoLine());
			double histoHeight = anYTrans.coordsToScreenPixel(theData.getBaseline());
			for (int q = 0; q < aPointList.size(); q++) {
				DataPoint aPoint1 = aPointList.get(q);
				DataPoint aPoint2 = aPointList.get(q == aPointList.size() -1 ? q-1 : q + 1);

				double x1 = anXTrans.coordsToScreenPixel(aPoint1.getX());
				double y1 = anYTrans.coordsToScreenPixel(aPoint1.getY());

				double x2 = anXTrans.coordsToScreenPixel(aPoint2.getX());
				double y2 = anYTrans.coordsToScreenPixel(aPoint2.getY());

				double hw = Math.abs((x2 - x1) / 2.0);
				if (aPath2.getList().isEmpty()) {
					aPath2.addPoint(new Vector2<Double>(x1 - hw, histoHeight));
				}
				aPath2.addPoint(new Vector2<Double>(x1 - hw, y1));
				histoHeight = y1;
				aPath2.addPoint(new Vector2<Double>(x1 + hw, histoHeight));
				if (q == aPointList.size()-1) {
					aPath2.addPoint(new Vector2<Double>(x1+hw, anYTrans.coordsToScreenPixel(theData.getBaseline())));
				}
			}
			aList.add(aPath2);
		}

		aList.stream().forEach(elem -> elem.setClipPath(thePad.getId()));
		return aList;
	}

	private List<Element> renderYErrorBar(Marker theMarker, double x, double y, double eyU, double eyD) {
		if (theMarker == null || !theMarker.getError()) {
			return Collections.emptyList();
		}
		List<Element> aList = new ArrayList<Element>();

		Stroke anErrorBarStroke = new Stroke();
		anErrorBarStroke.setColor(theMarker.getErrorColor());
		anErrorBarStroke.setWidth(theMarker.getErrorWeight());

		double aHalfSize = theMarker.getSize() / 2.0;

		Line aLine = new Line(new Vector2<Double>(x, eyU), new Vector2<Double>(x, eyD));
		aLine.setStroke(anErrorBarStroke);
		aList.add(aLine);
		Line aTopLine = new Line(new Vector2<Double>(x - aHalfSize, eyU), new Vector2<Double>(x + aHalfSize, eyU));
		aTopLine.setStroke(anErrorBarStroke);
		aList.add(aTopLine);
		Line aBotLine = new Line(new Vector2<Double>(x - aHalfSize, eyD), new Vector2<Double>(x + aHalfSize, eyD));
		aBotLine.setStroke(anErrorBarStroke);
		aList.add(aBotLine);

		return aList;
	}

	public List<Element> renderMarker(Marker marker, double x, double y, DataPoint aPoint) {
		if (marker == null) {
			return Collections.emptyList();
		}
		List<Element> aList = new ArrayList<Element>();

		double aFullSize = marker.getSize();
		double aHalfSize = aFullSize / 2.0;

		Stroke aStroke = new Stroke();
		aStroke.setColor(marker.getColor());
		aStroke.setWidth(marker.getWeight());

		Line aLine;

		switch (marker.getStyle()) {
		case X:
			aLine = new Line(new Vector2<Double>(x - aHalfSize, y - aHalfSize),
					new Vector2<Double>(x + aHalfSize, y + aHalfSize));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			aLine = new Line(new Vector2<Double>(x + aHalfSize, y - aHalfSize),
					new Vector2<Double>(x - aHalfSize, y + aHalfSize));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			break;
		case TRIANGLE:
			Path aPath = new Path();
			aPath.setStroke(aStroke);
			if (marker.getFill() != null) {
				aPath.setFill(marker.getFill());
			}
			aPath.addPoint(new Vector2<Double>(x - aHalfSize, y - aHalfSize));
			aPath.addPoint(new Vector2<Double>(x, y + aHalfSize));
			aPath.addPoint(new Vector2<Double>(x + aHalfSize, y - aHalfSize));
			aPath.setClose(true);
			aList.add(aPath);
			break;
		case REVERSE_TRIANGLE:
			Path aPath2 = new Path();
			aPath2.setStroke(aStroke);
			if (marker.getFill() != null) {
				aPath2.setFill(marker.getFill());
			}
			aPath2.addPoint(new Vector2<Double>(x - aHalfSize, y + aHalfSize));
			aPath2.addPoint(new Vector2<Double>(x, y - aHalfSize));
			aPath2.addPoint(new Vector2<Double>(x + aHalfSize, y + aHalfSize));
			aPath2.setClose(true);
			aList.add(aPath2);
			break;
		case BOX:
			Rect aRect = new Rect(new Vector2<Double>(x - aHalfSize, y - aHalfSize),
					new Vector2<Double>(aFullSize, aFullSize));
			aRect.setStroke(aStroke);
			if (marker.getFill() != null) {
				aRect.setFill(marker.getFill());
			}
			aList.add(aRect);
			break;
		case CIRCLE:
			Circle aCircle = new Circle(new Vector2<Double>(x, y), aHalfSize);
			aCircle.setStroke(aStroke);
			if (marker.getFill() != null) {
				aCircle.setFill(marker.getFill());
			}
			aList.add(aCircle);

			break;
		case PLUS:
			aLine = new Line(new Vector2<Double>(x, y - aHalfSize), new Vector2<Double>(x, y + aHalfSize));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			aLine = new Line(new Vector2<Double>(x - aHalfSize, y), new Vector2<Double>(x + aHalfSize, y));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			break;
		case DIAMOND:
			// TODO: Could we do this with a rect + rotation?

			aLine = new Line(new Vector2<Double>(x, y - aHalfSize), new Vector2<Double>(x + aHalfSize, y));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			aLine = new Line(new Vector2<Double>(x + aHalfSize, y), new Vector2<Double>(x, y + aHalfSize));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			aLine = new Line(new Vector2<Double>(x, y + aHalfSize), new Vector2<Double>(x - aHalfSize, y));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			aLine = new Line(new Vector2<Double>(x - aHalfSize, y), new Vector2<Double>(x, y - aHalfSize));
			aLine.setStroke(aStroke);
			aList.add(aLine);

			break;
		default:
			break;
		}

		return aList;
	}
}
