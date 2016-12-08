package com.jraph.plotter.renderer;

import java.util.Collection;

import com.jraph.plotter.Histo;
import com.jraph.plotter.components.Pad;
import com.jraph.svg.Element;

public abstract class HistoRenderer {
	public abstract Collection<Element> render(Pad thePad, Histo theHisto);
}
