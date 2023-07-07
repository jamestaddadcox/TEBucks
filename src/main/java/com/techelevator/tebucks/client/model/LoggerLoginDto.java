package com.techelevator.tebucks.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoggerLoginDto {

	private String username;
	private String password;
	@JsonProperty("api_key")
	private int apiKey;
	private int userId;
	private String token;


	public LoggerLoginDto () {
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public int getApiKey () {
		return apiKey;
	}

	public void setApiKey (int apiKey) {
		this.apiKey = apiKey;
	}

	public int getUserId () {
		return userId;
	}

	public void setUserId (int userId) {
		this.userId = userId;
	}

	public String getToken () {
		return token;
	}

	public void setToken (String token) {
		this.token = token;
	}
}
