package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.RegisteredUser;

public class RegisteredUserImpl extends Persistent implements RegisteredUser {



	private String name;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String phone;
	private boolean canText;
	private boolean isAdmin;


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;

	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;

	}

	@Override
	public boolean getIsAdmin() {
		return isAdmin;
	}

	@Override
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;

	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;

	}

	@Override
	public String getPhone() {
		return this.phone;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;

	}

	@Override
	public boolean getCanText() {
		return this.canText;
	}

	@Override
	public void setCanText(boolean canText) {
		this.canText = canText;

	}

}
