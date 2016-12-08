package com.jraph.plotter.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.jraph.plotter.Data;
import com.jraph.plotter.components.Label;
import com.jraph.plotter.components.Pad;
import com.jraph.plotter.helper.XCoordsTransformer;
import com.jraph.plotter.helper.YCoordsTransformer;
import com.jraph.plotter.renderer.label.AxisLabelRenderer;
import com.jraph.plotter.renderer.label.LabelRenderer;
import com.jraph.plotter.renderer.label.PadTitleRenderer;
import com.jraph.svg.Element;
import com.jraph.svg.elements.ClipPath;
import com.jraph.svg.elements.Line;
import com.jraph.svg.elements.Rect;
import com.jraph.svg.utils.Vector2;

public class PadRenderer {
	public static final PadRenderer _instance = new PadRenderer();

	public static PadRenderer getInstance() {
		return _instance;
	}

	private PadRenderer() {

	}

	public List<Element> render(Pad thePad) {
		List<Element> aList = new ArrayList<>();
		if (thePad.getHisto() != null) {
			aList.addAll(thePad.getHisto().getRenderer().render(thePad, thePad.getHisto()));
		}
		List<Data> revData = new ArrayList<>(thePad.getDatas());
		Collections.reverse(revData);
		for (Data aData : revData) {
			aList.addAll(aData.getRenderer().render(thePad, aData));
		}
		PadTitleRenderer aPadTitleRenderer = PadTitleRenderer.getInstance();
		aList.addAll(aPadTitleRenderer.render(thePad));

		AxisRenderer anAxisRenderer = AxisRenderer.getInstance();
		aList.addAll(anAxisRenderer.render(thePad.getXAxisBottom()));
		aList.addAll(anAxisRenderer.render(thePad.getXAxisTop()));
		aList.addAll(anAxisRenderer.render(thePad.getYAxisLeft()));
		aList.addAll(anAxisRenderer.render(thePad.getYAxisRight()));

		AxisLabelRenderer anAxisLabelRenderer = AxisLabelRenderer.getInstance();
		aList.addAll(anAxisLabelRenderer.render(thePad.getXLabel()));
		aList.addAll(anAxisLabelRenderer.render(thePad.getYLabel()));

		LabelRenderer aLabelRenderer = LabelRenderer.getInstance();
		for (Entry<Label, Vector2<Double>> anEntry : thePad.getLabels().entrySet()) {
			aList.addAll(aLabelRenderer.render(anEntry.getKey().getText(), (int) anEntry.getKey().getSize(),
					anEntry.getValue(), anEntry.getKey().getColor()));
		}

		if (thePad.getZeroLineStroke() != null) {
			XCoordsTransformer aCoordTransX = thePad.getXCoordTransformer();
			YCoordsTransformer aCoordTransY = thePad.getYCoordTransformer();
			double sX = aCoordTransX.coordsToScreenPixel(thePad.getXAxisBottom().getRange().getMin());
			double eX = aCoordTransX.coordsToScreenPixel(thePad.getXAxisBottom().getRange().getMax());
			double zY = aCoordTransY.coordsToScreenPixel(0);
			Line aLine = new Line(new Vector2<>(sX, zY), new Vector2<>(eX, zY));
			aLine.setStroke(thePad.getZeroLineStroke());
			aLine.setClipPath(thePad.getId());
			aList.add(aLine);
		}

		ClipPath aClipPath = new ClipPath(thePad.getId());
		Rect aClip = new Rect();
		aClip.setStroke(null);
		aClip.getPosition().set(thePad.getMargin().getLeft(), thePad.getMargin().getTop());
		Vector2<Integer> aPosition = thePad.getDimension();
		aClip.getDimension()
		.set(new Vector2<>(
				(double) aPosition.getX() - thePad.getMargin().getLeft() - thePad.getMargin().getRight(),
				(double) aPosition.getY() - thePad.getMargin().getTop() - thePad.getMargin().getBottom()));
		aList.add(aClip);
		aClipPath.addAll(Collections.singletonList(aClip));
		aList.add(aClipPath);

		KeyRenderer aKeyRenderer = KeyRenderer.getInstance();

		aList.addAll(aKeyRenderer.renderKey(thePad));

		return aList;
	}
}
