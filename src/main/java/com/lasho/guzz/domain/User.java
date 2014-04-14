package com.lasho.guzz.domain;

public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7797243281103328954L;

	private int id;

	private String userName;

	private int messageCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

}