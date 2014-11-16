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
	
	public RegisteredUserImpl( String name,
			   String firstName,
			   String lastName,
			   String password,
			   boolean isAdmin,
               String email,
			   String phone,
	 		   boolean canText
			   )
		{
				super( -1 );
				this.name = name;
				this.password = password;
				this.email = email;
				this.firstName = firstName;
				this.lastName = lastName;
				this.phone = phone;
				this.canText = canText;
				this.isAdmin = isAdmin;
}
	
	
	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public void setName(String name) {

		this.name=name;
	}

	@Override
	public String getFirstName() {

		return this.firstName;
	}

	@Override
	public void setFirstName(String firstName) {

		this.firstName=firstName;
	}

	@Override
	public String getLastName() {

		return this.lastName;
	}

	@Override
	public void setLastName(String lastName) {

		this.lastName=lastName;
	}

	@Override
	public String getPassword() {

		return this.password;
	}

	@Override
	public void setPassword(String password) {

		this.password=password;
	}

	@Override
	public boolean getIsAdmin() {

		return this.isAdmin;
	}

	@Override
	public void setIsAdmin(boolean isAdmin) {

		this.isAdmin=isAdmin;
	}

	@Override
	public String getEmail() {

		return this.email;
	}

	@Override
	public void setEmail(String email) {

		this.email=email;
	}

	@Override
	public String getPhone() {

		return this.phone;
	}

	@Override
	public void setPhone(String phone) {

		this.phone=phone;
	}

	@Override
	public boolean getCanText() {

		return this.canText;
	}

	@Override
	public void setCanText(boolean canText) {

		this.canText=canText;
	}

    public String toString()
    {
        return this.getFirstName() +" " + this.getLastName();
    }
}
