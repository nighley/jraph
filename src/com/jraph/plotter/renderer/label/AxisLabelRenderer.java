package com.jraph.plotter.renderer.label;

import java.util.Collections;
import java.util.List;

import com.jraph.plotter.components.axis.AxisLabel;
import com.jraph.plotter.components.axis.AxisOrientation;
import com.jraph.plotter.props.Inset;
import com.jraph.svg.Element;
import com.jraph.svg.utils.Vector2;
import com.jraph.svg.utils.Vector3;

public class AxisLabelRenderer {
	private static final AxisLabelRenderer _instance = new AxisLabelRenderer();

	public static AxisLabelRenderer getInstance() {
		return _instance;
	}

	private AxisLabelRenderer() {

	}

	public List<Element> render(AxisLabel label) {
		if (label == null) {
			return Collections.emptyList();
		}
		return renderLabel(label.getPad().getDimension(), label.getPad().getMargin(), label,
				label.getAxisOrientation());
	}

	private List<Element> renderLabel(Vector2<Integer> theSize, Inset theMargin, AxisLabel theLabel,
			AxisOrientation theDirection) {
		Vector2<Double> aPosition = new Vector2<Double>(0.0, 0.0);
		Vector3<Double> aRotation = new Vector3<Double>(0.0, 0.0, 0.0);

		switch (theDirection) {
		case HORIZONTAL_BOTTOM:
			aPosition.setX((theSize.getX() - theMargin.getRight() - theMargin.getLeft()) / 2.0 + theMargin.getLeft()+  theLabel.getOffset().getX());
			aPosition.setY(theSize.getY() - theMargin.getBottom() / 2.0 + theLabel.getOffset().getY());
			break;
		case HORIZONTAL_TOP:
			break;
		case VERTICAL_LEFT:
			aPosition.setX(theMargin.getLeft() / 2.0 - theLabel.getOffset().getY());
			aPosition.setY((theSize.getY() - theMargin.getTop() - theMargin.getBottom()) / 2.0 + theMargin.getTop() +  theLabel.getOffset().getX());
			aRotation.setX(-90.0);
			break;
		case VERTICAL_RIGHT:
			break;
		default:
			break;
		}

		aRotation.setY(aPosition.getX());
		aRotation.setZ(aPosition.getY());
		return LabelRenderer.getInstance().render(theLabel.getText(), (int)theLabel.getSize(), aPosition, aRotation);
	}
}
