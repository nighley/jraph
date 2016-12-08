package com.jraph.plotter.renderer;

import java.util.Collection;

import com.jraph.plotter.Data;
import com.jraph.plotter.components.Pad;
import com.jraph.svg.Element;

public abstract class DataRenderer {
	public abstract Collection<Element> render(Pad thePad, Data aData);
}
