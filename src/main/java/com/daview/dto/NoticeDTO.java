package com.daview.dto;


public class NoticeDTO {
    private String title;
    private boolean isFixed;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isFixed() {
		return isFixed;
	}
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
}