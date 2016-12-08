package com.jraph.svg;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.jraph.svg.utils.NumberUtil;
import com.jraph.svg.utils.Stroke;
import com.jraph.svg.utils.SvgColor;
import com.jraph.svg.utils.Vector2;

public abstract class Element {
	private static final String SINGLE_TAG = "<%s %s/>";
	private static final String CONTENT_TAG = "<%s %s>%s</%s>";
	private static final String PARAMETER = "%s=\"%s\" ";

	private String tag;
	private SortedSet<Parameter<?>> parameters;
	private String content;

	private String clipPath = null;

	private Vector2<Double> pos;
	private Vector2<Double> dim;
	private String id;
	private String fillUrl;
	private Stroke stroke;
	private SvgColor fill;
	private String maskUrl;
	private boolean dimInPercent = false;

	public Element(String theTag) {
		tag = theTag;
		parameters = new TreeSet<>();
	}

	public String toSvg() {
		initParameters();
		if (content == null) {
			return String.format(SINGLE_TAG, tag, getParameterTags());
		} else {
			return String.format(CONTENT_TAG, tag, getParameterTags(), content, tag);
		}
	}

	private String getParameterTags() {
		StringBuilder aBuilder = new StringBuilder();
		for (Parameter<?> anEntry : parameters) {
			String aTag = anEntry.getKey().getRealTag() == null ? anEntry.getKey().name().toLowerCase().replace("_", "-") : anEntry.getKey().getRealTag();
			aBuilder.append(String.format(PARAMETER, aTag,
					anEntry.getValue().toString()));
		}
		return aBuilder.toString();
	}

	public void removeParameter(Attribute fill) {
		parameters = new TreeSet<Parameter<?>>(
				parameters.stream().filter(param -> param.getKey() != fill).collect(Collectors.toSet()));
	}

	protected void setContent(String theContent) {
		content = theContent;
	}

	protected void addParameter(Parameter<?> theParam) {
		parameters.add(theParam);
	}

	private double round(double d) {
		return NumberUtil.round(d);
	}

	protected void initParameters() {
		if (id != null) {
			addParameter(new Parameter<String>(Attribute.ID, id));
		}

		if (clipPath != null) {
			addParameter(new Parameter<String>(Attribute.CLIP_PATH, "url(#" + clipPath + ")"));
		}
		if (maskUrl != null) {
			addParameter(new Parameter<String>(Attribute.MASK, "url(#" + maskUrl + ")"));
		}
		if (pos != null) {
			addParameter(new Parameter<Double>(Attribute.X, round(pos.getX())));
			addParameter(new Parameter<Double>(Attribute.Y, round(pos.getY())));
		}
		if (dim != null) {
			if (dimInPercent) {
				addParameter(new Parameter<String>(Attribute.WIDTH, round(dim.getX()) + "%"));
				addParameter(new Parameter<String>(Attribute.HEIGHT, round(dim.getY()) + "%"));
			} else {
				addParameter(new Parameter<Double>(Attribute.WIDTH, round(dim.getX())));
				addParameter(new Parameter<Double>(Attribute.HEIGHT, round(dim.getY())));
			}
		}

		if (stroke != null) {
			addParameter(new Parameter<String>(Attribute.STROKE, stroke.getColor().toRGBLine()));
			addParameter(new Parameter<Double>(Attribute.STROKE_WIDTH, stroke.getWidth()));
			if (stroke.getStrokeDashArray() != null) {
				StringBuilder aBuilder = new StringBuilder();
				for (int q = 0; q < stroke.getStrokeDashArray().length; q++) {
					if (q != 0) {
						aBuilder.append(",");
					}
					aBuilder.append(stroke.getStrokeDashArray()[q]);
				}
				addParameter(new Parameter<String>(Attribute.STROKE_DASHARRAY, aBuilder.toString()));
			}
		}

		if (fillUrl != null) {
			addParameter(new Parameter<String>(Attribute.FILL, "url(#" + fillUrl + ")"));
		}
		if (fill == null) {
			addParameter(new Parameter<String>(Attribute.FILL, "none"));
		} else {
			addParameter(new Parameter<String>(Attribute.FILL, fill.toString()));
			addParameter(new Parameter<Double>(Attribute.FILL_OPACITY, fill.getA()));
		}
	}

	public void setClipPath(String thePath) {
		clipPath = thePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMask(String theId) {
		maskUrl = theId;
	}
	
	public String getFillUrl() {
		return fillUrl;
	}

	public void setFillUrl(String fillUrl) {
		this.fillUrl = fillUrl;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public SvgColor getFill() {
		return fill;
	}

	public void setFill(SvgColor fillColor) {
		this.fill = fillColor;
	}

	public Vector2<Double> getPosition() {
		return pos;
	}

	public void setPosition(Vector2<Double> pos) {
		this.pos = pos;
	}

	public Vector2<Double> getDimension() {
		return dim;
	}

	public void setDimension(Vector2<Double> dim) {
		this.dim = dim;
	}

	public void setDimInPercent(boolean perc) {
		dimInPercent = perc;
	}
}
