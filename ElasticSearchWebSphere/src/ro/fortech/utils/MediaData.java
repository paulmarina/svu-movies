package ro.fortech.utils;

import java.io.Serializable;

public class MediaData implements Serializable {

	private static final long serialVersionUID = -5477197941693046894L;

	Integer Width = 110;
	Integer Height = 50;

	public MediaData() {
	}

	public Integer getHeight() {
		return Height;
	}

	public void setHeight(Integer height) {
		Height = height;
	}

	public Integer getWidth() {
		return Width;
	}

	public void setWidth(Integer width) {
		Width = width;
	}
}
