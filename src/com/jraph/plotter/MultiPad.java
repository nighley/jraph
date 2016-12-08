package com.jraph.plotter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jraph.plotter.components.Label;
import com.jraph.plotter.components.Pad;
import com.jraph.plotter.props.Inset;
import com.jraph.plotter.renderer.PadRenderer;
import com.jraph.plotter.renderer.label.LabelRenderer;
import com.jraph.svg.Exportable;
import com.jraph.svg.Svg;
import com.jraph.svg.elements.Rect;
import com.jraph.svg.elements.TransformElement;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;
import com.jraph.svg.utils.Vector3;

public class MultiPad implements Exportable {
	private Vector2<Integer> size;
	private Inset margins;
	private Label canvasTitle;
	private double canvasTitleOffset = 10;
	private Label xLabel;
	private double xLabelOffset = 10;
	private Label yLabel;
	private double yLabelOffset = 10;
	private List<Pad> pads;

	public MultiPad() {
		size = new Vector2<>(800, 400);
		pads = new ArrayList<>();
		margins = new Inset(40, 40, 40, 40);
		xLabel = new Label("X-Label");
		yLabel = new Label("Y-Label");
		canvasTitle = new Label("Title");
	}

	@Override
	public Svg exportSvg() {
		Svg anSvg = new Svg(size);
		render(anSvg);
		return anSvg;
	}

	public void divide(int dx, int dy) {
		pads.clear();
		double aTotalWidth = size.getX() - margins.getLeft() - margins.getRight();
		double aTotalHeight = size.getY() - margins.getTop() - margins.getBottom();
		double aWidth = aTotalWidth / dx;
		double aHeight = aTotalHeight / dy;
		for (int r = 0; r < dy; r++) {
			for (int q = 0; q < dx; q++) {
				Pad aPad = new Pad(new Vector2<>((int) aWidth, (int) aHeight));
				aPad.setXLabel(null);
				aPad.setYLabel(null);
				aPad.getMargin().set(0, 0, 0, 0);
				aPad.getOffset().set(margins.getLeft() + aWidth * q, margins.getTop() + aHeight * r);
				pads.add(aPad);
			}
		}
	}

	private void render(Svg theSvg) {
		Rect aRect = new Rect();
		aRect.setDimension(new Vector2<>((double) getSize().getX(), (double) getSize().getY()));
		aRect.setFill(SvgColor.WHITE);
		theSvg.addElement(aRect);
		if (canvasTitle != null) {
			double aHalfWidth = size.getX() / 2.0;
			theSvg.addAllElements(LabelRenderer.getInstance().render(canvasTitle.getText(),
					(int)canvasTitle.getSize(),
					new Vector2<>(aHalfWidth, getCanvasTitleOffset())));
		}
		if (xLabel != null) {
			double aHalfWidthMargin = (size.getX() - margins.getLeft() - margins.getRight()) / 2.0 + margins.getLeft();
			double aHeight = size.getY() - getxLabelOffset();
			theSvg.addAllElements(LabelRenderer.getInstance().render(xLabel.getText(), (int) xLabel.getSize(),
					new Vector2<>(aHalfWidthMargin, aHeight)));
		}
		if (yLabel != null) {
			double aHalfHeightMargin = (size.getY() - margins.getTop() - margins.getBottom()) / 2.0 + margins.getTop();
			theSvg.addAllElements(LabelRenderer.getInstance().render(yLabel.getText(), (int) yLabel.getSize(),
					new Vector2<>(getyLabelOffset(), aHalfHeightMargin),
					new Vector3<>(-90.0, getyLabelOffset(), aHalfHeightMargin)));
		}
		for (Pad aPad : pads) {
			TransformElement aTransformer = new TransformElement();
			aTransformer.addAll(PadRenderer.getInstance().render(aPad));
			aTransformer.setOffset(aPad.getOffset());
			theSvg.addAllElements(Collections.singletonList(aTransformer));
		}
	}

	public Label getCanvasTitle() {
		return canvasTitle;
	}

	public void setCanvasTitle(Label canvasTitle) {
		this.canvasTitle = canvasTitle;
	}

	public Label getxLabel() {
		return xLabel;
	}

	public void setxLabel(Label xLabel) {
		this.xLabel = xLabel;
	}

	public Label getyLabel() {
		return yLabel;
	}

	public void setyLabel(Label yLabel) {
		this.yLabel = yLabel;
	}

	public Vector2<Integer> getSize() {
		return size;
	}

	public void setSize(Vector2<Integer> theSize) {
		size = theSize;
	}

	public Pad getPad(int theIndex) {
		return pads.get(theIndex);
	}

	public Inset getMargin() {
		return margins;
	}

	public double getxLabelOffset() {
		return xLabelOffset;
	}

	public void setxLabelOffset(double xLabelOffset) {
		this.xLabelOffset = xLabelOffset;
	}

	public double getyLabelOffset() {
		return yLabelOffset;
	}

	public void setyLabelOffset(double yLabelOffset) {
		this.yLabelOffset = yLabelOffset;
	}

	public double getCanvasTitleOffset() {
		return canvasTitleOffset;
	}

	public void setCanvasTitleOffset(double canvasTitleOffset) {
		this.canvasTitleOffset = canvasTitleOffset;
	}

	public List<Pad> getPads() {
		return pads;
	}


}
