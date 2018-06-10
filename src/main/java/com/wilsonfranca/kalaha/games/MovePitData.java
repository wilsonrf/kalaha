package com.wilsonfranca.kalaha.games;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MovePitData {

	@NotEmpty
	private String pit;
	
	@Email
	private String user;

	@NotEmpty
	private String game;
	
	public String getPit() {
		return pit;
	}

	public void setPit(String pit) {
		this.pit = pit;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovePitData [pit=");
		builder.append(pit);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
}
