package com.exmek.commons.net;

public enum ContentType {

	TEXT_PLAIN("text", "plain"),

	TEXT_HTML("text", "html");

	private String primaryType;

	private String subType;

	private ContentType(String primaryType, String subType) {
		this.primaryType = primaryType;
		this.subType = subType;
	}

	public String getCanonicalName() {
		return this.primaryType + "/" + this.subType + "; charset=utf-8";
	}

	@Override
	public String toString() {
		return this.getCanonicalName();
	}
}
