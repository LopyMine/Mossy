package net.lopymine.mossy.modmenu.yacl.simple;

import lombok.Getter;

@Getter
public enum SimpleContent {
	NONE("none"),
	IMAGE("png"),
	WEBP("webp"),
	GIF("gif");

	private final String ext;

	SimpleContent(String ext) {
		this.ext = ext;
	}
}
