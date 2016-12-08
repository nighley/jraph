package com.jraph.plotter.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.jraph.plotter.Histo;
import com.jraph.plotter.components.Pad;
import com.jraph.plotter.helper.XCoordsTransformer;
import com.jraph.plotter.helper.YCoordsTransformer;
import com.jraph.plotter.props.Range;
import com.jraph.svg.Element;
import com.jraph.svg.elements.Line;
import com.jraph.svg.elements.Rect;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;

public class BasicHistoRenderer extends HistoRenderer {

	public static final BasicHistoRenderer _instance = new BasicHistoRenderer();

	public static BasicHistoRenderer getInstance() {
		return _instance;
	}

	private BasicHistoRenderer() {

	}

	@Override
	public Collection<Element> render(Pad thePad, Histo theHisto) {
		List<Element> aList = new ArrayList<Element>();
		XCoordsTransformer anXTrans = thePad.getXCoordTransformer();
		YCoordsTransformer anYTrans = thePad.getYCoordTransformer();

		double[] heightList = theHisto.getList();
		double min = theHisto.getzRange().getMin();
		double max = theHisto.getzRange().getMax();

		Range aRangeX = thePad.getXAxisBottom().getRange();
		Range aRangeY = thePad.getYAxisLeft().getRange();

		double xStep = aRangeX.getLength() / theHisto.getnBinsX();
		double yStep = aRangeY.getLength() / theHisto.getnBinsY();

		double xStart = aRangeX.getMin();
		double yStart = aRangeY.getMin();
		for (int y = 0; y < theHisto.getnBinsY(); y++) {
			for (int x = 0; x < theHisto.getnBinsX(); x++) {
				double xVal = anXTrans.coordsToScreenPixel(xStart + xStep * x);
				double yVal = anYTrans.coordsToScreenPixel(yStart + yStep * (y + 1));
				Rect aRect = new Rect(new Vector2<Double>(xVal, yVal), new Vector2<Double>(
						anXTrans.distanceCoordsToPixel(xStep), anYTrans.distanceCoordsToPixel(yStep)));
				double val = Math.max(min, Math.min(heightList[x + y * theHisto.getnBinsX()], max));
				double alpha = val;
				if (Math.abs(val - min) < (max - min) / 10000.0) {
					continue;
				}
				val -= (max - min) / 10000.0;
				if (theHisto.isLog()) {
					val = 1 - Math.log10(val - min + 1 ) / Math.log10(max - min + 1);
				} else {
					val /= (max - min);
					val = 1 - val;
				}
				SvgColor aCol = new SvgColor(((float) val * 0.7f));
				if (alpha < max/theHisto.getAlphaRange()) {
					aCol.setA(alpha/(max/theHisto.getAlphaRange()));
				}
				aRect.setFill(aCol);
				aRect.setStroke(null);
//				aRect.getStroke().setColor(aCol);
				aList.add(aRect);
			}
		}

		return aList;
	}
}
