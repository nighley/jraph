package com.jraph.plotter.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jraph.plotter.Data;
import com.jraph.plotter.DataPoint;
import com.jraph.plotter.FillPattern;
import com.jraph.plotter.Histo;
import com.jraph.plotter.components.Label;
import com.jraph.plotter.components.Pad;
import com.jraph.plotter.components.marker.Marker;
import com.jraph.plotter.helper.XCoordsTransformer;
import com.jraph.plotter.helper.YCoordsTransformer;
import com.jraph.plotter.renderer.label.LabelRenderer;
import com.jraph.svg.Element;
import com.jraph.svg.elements.Defs;
import com.jraph.svg.elements.Line;
import com.jraph.svg.elements.Mask;
import com.jraph.svg.elements.Path;
import com.jraph.svg.elements.Pattern;
import com.jraph.svg.elements.Rect;
import com.jraph.svg.elements.Text;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.TextAnchor;
import com.jraph.svg.utils.Vector2;
import com.jraph.svg.utils.Vector3;

public class KeyRenderer {
	public static final KeyRenderer _instance = new KeyRenderer();

	private KeyRenderer() {

	}

	public static KeyRenderer getInstance() {
		return _instance;
	}

	public List<Element> renderKey(Pad thePad) {
		if (thePad.getKeyPosition() == null) {
			return Collections.emptyList();
		}
		List<Element> aList = new ArrayList<>();
		Vector2<Double> aPos = thePad.getKeyPosition();
		if (thePad.getHisto() == null) {
			BasicDataRenderer aRenderer = BasicDataRenderer.getInstance();

			int maxWidth = 0;
			for (Data aData : thePad.getDatas()) {
				if (aData.getName() == null) {
					continue;
				}
				int len = aData.getName().length();
				maxWidth = Math.max(len, maxWidth);
			}
			if (thePad.getKeyTitle() != null) {
				Text aText = new Text();
				aText.setText(thePad.getKeyTitle());
				aText.setTextSize(thePad.getKeyTitleSize());
				aText.setTextAnchor(TextAnchor.START);
				aText.getPosition().set(new Vector2<>(aPos));
				aList.add(aText);
				aPos.add(new Vector2<>(0.0, thePad.getKeyTitleSize() + 2.0));
			}
			for (Data aData : thePad.getDatas()) {
				if (aData.getName() == null) {
					continue;
				}
				Text aText = new Text();
				aText.setTextSize(thePad.getKeyEntrySize());
				aText.setText(new Label(aData.getName()).getText());
				aText.setTextAnchor(TextAnchor.START);
				aText.getPosition().set(new Vector2<>(aPos).add(new Vector2<>(15.0, thePad.getKeyEntrySize() / 3.0)));
				aList.add(aText);

				Marker aMarker = aData.getMarker();
				Stroke aStroke = aData.getLine();
				FillPattern aFillPattern = aData.getFillPattern();
				if (aStroke == null) {
					aStroke = aData.getHistoLine();
				}
				if (aFillPattern != null) {
					Path aPath = new Path();
					aPath.addPoint(new Vector2<>(aPos.getX() - thePad.getKeyEntrySize() / 2.0, aPos.getY()));
					aPath.addPoint(new Vector2<>(aPos.getX() + thePad.getKeyEntrySize() / 2.0, aPos.getY()));
					aPath.addPoint(new Vector2<>(aPos.getX() + thePad.getKeyEntrySize() / 2.0,
							aPos.getY() + thePad.getKeyEntrySize()*0.8));
					aPath.addPoint(new Vector2<>(aPos.getX() - thePad.getKeyEntrySize() / 2.0,
							aPos.getY() + thePad.getKeyEntrySize()*0.8));
					aPath.setClose(true);

					Defs aDefs = new Defs();

					Pattern aPattern = new Pattern();
					aPattern.setPatternRot(aFillPattern.getRot());
					aPattern.setDimension(new Vector2<>(4.0, 4.0));

					Rect aRect = new Rect(null, new Vector2<>(3.0, 4.0)); // TODO
					aRect.setFill(SvgColor.WHITE);
					aRect.setStroke(null);
					aPattern.addChildren(aRect);
					aDefs.addChildren(aPattern);

					Mask aMask = new Mask();
					Rect aMaskRect = new Rect(new Vector2<>(0.0, 0.0), new Vector2<>(100.0, 100.0));
					aMaskRect.setStroke(null);
					aMaskRect.setDimInPercent(true);
					aMaskRect.setFillUrl(aPattern.getId());
					aMask.addChildren(aMaskRect);
					aDefs.addChildren(aMask);

					aList.add(aDefs);

					aPath.setMask(aMask.getId());
					aPath.setFill(aFillPattern.getFill());
					aList.add(aPath);

				}
				if (aMarker != null) {
					aList.addAll(aRenderer.renderMarker(aMarker, aPos.getX(), aPos.getY()+thePad.getKeyEntrySize()/3.0, new DataPoint(0, 0)));
				}
				if (aStroke != null) {
					Line aLine = new Line();
					aLine.setStroke(aStroke);
					aLine.setStart(new Vector2<>(aPos.getX() - thePad.getKeyEntrySize() / 2.0, aPos.getY()+thePad.getKeyEntrySize()/3.0));
					aLine.setEnd(new Vector2<>(aPos.getX() + thePad.getKeyEntrySize() / 2.0, aPos.getY()+thePad.getKeyEntrySize()/3.0));
					aList.add(aLine);
				}

				aPos.add(new Vector2<>(0.0, thePad.getKeyEntrySize() + 2.0));
			}
		} else if (thePad.getHisto().isRenderKey()) {
			Histo theHisto = thePad.getHisto();
			double[] heightList = theHisto.getList();
			double min = Arrays.stream(heightList).min().getAsDouble();
			double max = Arrays.stream(heightList).max().getAsDouble();
			double realMax = max;
			min = Math.max(0.1, min);

			XCoordsTransformer anXTrans = thePad.getXCoordTransformer();
			YCoordsTransformer anYTrans = thePad.getYCoordTransformer();
			double end = anXTrans.coordsToScreenPixel(thePad.getXAxisBottom().getRange().getMax());
			double startY = anYTrans.coordsToScreenPixel(thePad.getYAxisLeft().getRange().getMax());
			double endY = anYTrans.coordsToScreenPixel(thePad.getYAxisLeft().getRange().getMin());

			double step = Math.abs(endY - startY) / 100.0;
			for (int q = 0; q < 100; q++) {
				Vector2<Double> copyStart = new Vector2<>(end + 6, startY);
				copyStart.add(new Vector2<>(0.0, step * q));
				Rect aRect = new Rect(copyStart, new Vector2<>(20.0, step));
				float value = q / 100.0f;
				value *= 0.7f;
				SvgColor aColor = new SvgColor(value);
				aRect.setFill(aColor);
				aRect.getStroke().setColor(aColor);
				aList.add(aRect);
			}
			Vector2<Double> copyStart = new Vector2<>(end + 6, startY);
			Rect aRect = new Rect(copyStart, new Vector2<>(20.0, step * 100));
			aRect.setFill(null);
			aRect.getStroke().setColor(SvgColor.BLACK);
			aList.add(aRect);
			if (theHisto.getzAxisLabel() != null) {
				Vector2<Double> aPosition = new Vector2<>(0.0, 0.0);
				Vector3<Double> aRotation = new Vector3<>(0.0, 0.0, 0.0);
				aPosition.setX(end + 20 + 6 + 30 + theHisto.getzLabelOffset());
				aPosition.setY(Math.abs(startY - endY) / 2.0);
				aRotation.setX(-90.0);
				aRotation.setY(aPosition.getX());
				aRotation.setZ(aPosition.getY());
				aList.addAll(LabelRenderer.getInstance().render(theHisto.getzAxisLabel().getText(),
						(int) theHisto.getzAxisLabel().getSize(), aPosition, aRotation));
			}

			double scale = theHisto.getScale();
			if (theHisto.isLog()) {
				min = Math.log10(min);
				max = Math.log10(max);
				scale = 0.1;
			}
			do {
				
				double pos; // FIXME wrong
				if (theHisto.isLog()) {
					pos = 0;
				}else {
					pos = -scale;
				}
				double yDis = Math.abs(startY - endY);
				double yDisLog = Math.abs(max - min);
				int num = 100;
				if (theHisto.isLog()) {
					num = 10;
				}
				for (int q = 0; q < num; q++) {
					pos += scale;

					if (pos < realMax) {
						double y = pos;
						if (theHisto.isLog()) {
							y = Math.log10(pos)-min;
						}
						double ratio = y / yDisLog;
						if (endY - ratio * yDis < startY) {
							break;
						}
						Line aLine = new Line(new Vector2<>(end + 20 + 6, endY - ratio * yDis),
								new Vector2<>(end + 6, endY - ratio * yDis));
						aLine.setStroke(new Stroke());
						aList.add(aLine);
						if (q == 0 || !theHisto.isLog()) {
							if (pos + scale > realMax && theHisto.iszIgnoreHighest()) {

							} else {
								Text aText = new Text();
								aText.setText(String.format("%.1f", pos));
								aText.setPosition(new Vector2<>(end + 20 + 6 + aText.getText().length() * 4,
										endY - ratio * yDis));
								aList.add(aText);
							}
						} else {

						}
					} else {
						break;
					}
				}
				if (theHisto.isLog()) {
					scale *= 10;
				}
			} while (scale / 10 < realMax && theHisto.isLog());
		}
		return aList;
	}

}
