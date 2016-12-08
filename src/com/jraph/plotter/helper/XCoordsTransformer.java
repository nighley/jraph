package com.jraph.plotter.helper;

import com.jraph.plotter.props.Range;

public class XCoordsTransformer extends CoordsTransformer {
    public XCoordsTransformer(Range coords, Range pixel, boolean log) {
        super(coords, pixel, log);
    }

    @Override
    public double coordsToScreenPixel(double c) {
        return coordsToPixel(c);
    }

    @Override
    public double screenPixelToCoords(double p) {
        return pixelToCoords(p);
    }
}
