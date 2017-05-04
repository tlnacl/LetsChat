package com.tlnacl.letschat.channel.model;

public class UserSearchResult {
	private final User user;

	public UserSearchResult(User user) {
		this.user = user;
	}

	public UserSearchResult() {
		this.user = null;
	}

	public boolean isSuccess() {
		return user != null;
	}

	public User getUser() {
		return user;
	}
}
