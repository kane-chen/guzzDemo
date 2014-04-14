package com.lasho.guzz.domain;

import java.util.Date;

public class Message implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8645442937709740683L;

	private int id;
	
	private int userId ;
	
	private String content;

	private Date createdTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}