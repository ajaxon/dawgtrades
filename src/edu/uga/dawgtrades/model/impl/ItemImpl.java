package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ItemImpl extends Persistent implements Item {
		
		private Category category;
		private RegisteredUser user;
		private String identifier;
		private String name;
		private String description;
		private long userId;
		private long categoryId;
		
	public ItemImpl(Category category, RegisteredUser user, String identifier,
			String name, String description) {
			
			super(-1);
			this.setCategory(category);
			this.categoryId=category.getId();
			this.setUser(user);
			this.userId=user.getId();
			this.identifier=identifier;
			this.name=name;
			this.description=description;
		

	
	}
	
	public ItemImpl(long categoryId, long userId, String identifier,
			String name, String description) {
			super(-1);
			this.categoryId=categoryId;
			this.userId=userId;
			this.identifier=identifier;
			this.name=name;
			this.description=description;

	}
	
	

	@Override
	public String getIdentifier() {

		return this.identifier;
	}

	@Override
	public void setIdentifier(String identifier) {

		this.identifier=identifier;
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
	public String getDescription() {

		return this.description;
	}

	@Override
	public void setDescription(String description) {

		this.description=description;
	}

	@Override
	public long getCategoryId() {

		return this.categoryId;
	}

	@Override
	public void setCategoryId(long categoryId) {

		this.categoryId=categoryId;
	}

	@Override
	public long getOwnerId() {
		
		
		return this.userId;
	}

	@Override
	public void setOwnerId(long ownerId) {

		this.userId=ownerId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

}
