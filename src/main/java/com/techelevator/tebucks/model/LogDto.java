package com.techelevator.tebucks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogDto {
	private String description;
	@JsonProperty("username_from")
	private String usernameFrom;
	private String username_to;
	private double amount;

	public LogDto (String description, String usernameFrom, String username_to, int amount, int log_id, String createdDate) {
		this.description = description;
		this.usernameFrom = usernameFrom;
		this.username_to = username_to;
		this.amount = amount;
	}

	public LogDto () {
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public String getUsernameFrom () {
		return usernameFrom;
	}

	public void setUsernameFrom (String usernameFrom) {
		this.usernameFrom = usernameFrom;
	}

	public String getUsername_to () {
		return username_to;
	}

	public void setUsername_to (String username_to) {
		this.username_to = username_to;
	}

	public double getAmount () {
		return amount;
	}

	public void setAmount (double amount) {
		this.amount = amount;
	}

}
