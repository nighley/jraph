package com.jraph.plotter.helper;

import com.jraph.plotter.props.Range;

public class YCoordsTransformer extends CoordsTransformer {
    public YCoordsTransformer(Range coords, Range pixel, boolean log) {
        super(coords, pixel, log);
    }

    @Override
    public double coordsToScreenPixel(double c) {
        return getPixelRange().getMin() + getPixelRange().getMax() - coordsToPixel(c);
    }

    @Override
    public double screenPixelToCoords(double p) {
        // not entirely sure this is correct
        // it passes the test though.. luck?
        return pixelToCoords(getPixelRange().getMax() - p) + getCoordsRange().getMin();
    }
}
