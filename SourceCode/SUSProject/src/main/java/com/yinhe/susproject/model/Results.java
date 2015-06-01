package com.yinhe.susproject.model;

import java.util.List;

public class Results {
	private String update;
	private String url;
	private List<String> reserved;
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public List<String> getReserved() {
		return reserved;
	}
	public void setReserved(List<String> reserved) {
		this.reserved = reserved;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
