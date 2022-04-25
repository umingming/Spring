package com.parser.jdom;

import lombok.Data;

@Data
public class Tag {
	private String tag;
	private String name;
	
	public Tag(String tag) {
		this.tag = tag;
	}
}
