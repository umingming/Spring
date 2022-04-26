package com.aim;

import lombok.Data;

@Data
public class Tag {
	private TagName tag;
	private String name;
	
	public Tag(String tag) {
		this.tag = TagName.valueOf(tag);
	}
	
	public String getTag() {
		return tag.toString();
	}
}
