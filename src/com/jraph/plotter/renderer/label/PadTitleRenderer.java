package com.jraph.plotter.renderer.label;

import java.util.Collections;
import java.util.List;

import com.jraph.plotter.components.Pad;
import com.jraph.svg.Element;
import com.jraph.svg.utils.Vector2;

public class PadTitleRenderer {
	private final static PadTitleRenderer _instance = new PadTitleRenderer();

	public static final PadTitleRenderer getInstance() {
		return _instance;
	}

	private PadTitleRenderer() {

	}

	public List<Element> render(Pad thePad) {
		if (thePad.getTitle() == null) {
			return Collections.emptyList();
		}
		double aHalfWidth = thePad.getDimension().getX() / 2.0;
		return LabelRenderer.getInstance().render(thePad.getTitle().getText(), (int)thePad.getTitle().getSize(),
				new Vector2<Double>(aHalfWidth+thePad.getTitleOffset().getX(), thePad.getTitleOffset().getY()));
	}

}
