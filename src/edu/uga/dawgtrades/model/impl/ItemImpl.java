package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ItemImpl extends Persistent implements Item {


	private String identifier;
	private String name;
	private String description;
	private long userId;
	private long categoryId;

	public ItemImpl(Category category, RegisteredUser user, String identifier,
					String name, String description) throws DTException {

		super(-1);
		if (category == null)
			throw new DTException("Category is null");
		if (!category.isPersistent())
			throw new DTException("Category is not persistent");
		if (user == null)
			throw new DTException("User is null");
		if (!user.isPersistent())
			throw new DTException("User is not persistent");

		this.categoryId = category.getId();

		this.userId = user.getId();
		this.identifier = identifier;
		this.name = name;
		this.description = description;


	}

	public ItemImpl(long categoryId, long userId, String identifier,
					String name, String description) {
		this.categoryId = categoryId;
		this.userId = userId;
		this.identifier = identifier;
		this.name = name;
		this.description = description;

	}


	@Override
	public String getIdentifier() {

		return this.identifier;
	}

	@Override
	public void setIdentifier(String identifier) {

		this.identifier = identifier;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String getDescription() {

		return this.description;
	}

	@Override
	public void setDescription(String description) {

		this.description = description;
	}

	@Override
	public long getCategoryId() {

		return this.categoryId;
	}

	@Override
	public void setCategoryId(long categoryId) {

		this.categoryId = categoryId;
	}

	@Override
	public long getOwnerId() {


		return this.userId;
	}

	@Override
	public void setOwnerId(long ownerId) {

		this.userId = ownerId;
	}
}

