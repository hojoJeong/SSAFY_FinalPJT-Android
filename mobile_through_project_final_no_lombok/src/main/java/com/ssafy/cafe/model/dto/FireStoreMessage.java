package com.ssafy.cafe.model.dto;

import java.util.ArrayList;

public class FireStoreMessage {
    private ArrayList<String> tokenList;
    public ArrayList<String> getTokenList() {
		return tokenList;
	}
	public void setTokenList(ArrayList<String> tokenList) {
		this.tokenList = tokenList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	private String title;
    private String body;
}
