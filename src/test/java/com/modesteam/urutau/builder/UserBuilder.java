package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.UrutaUser;

public class UserBuilder {
	private String email;
	private String login;
	private String name;
	private String lastName;
	private String password;
	private String passwordVerify;
	private Long id;

	public UserBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder email(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder login(String login) {
		this.login = login;
		return this;
	}

	public UserBuilder name(String name) {
		this.name = name;
		return this;
	}

	public UserBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder password(String password) {
		this.password = password;
		return this;
	}

	public UserBuilder passwordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
		return this;
	}

	public UrutaUser build() {
		UrutaUser user = new UrutaUser();
		user.setUserID(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setPasswordVerify(passwordVerify);
		return user;
	}
}
