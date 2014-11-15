package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;

public class AttributeTypeImpl extends Persistent implements AttributeType {
	
	private long categoryId;
	private String name;

	public AttributeTypeImpl(Category category, String name) throws DTException {
		if(category == null)
			throw new DTException("Category is null");
		if(!category.isPersistent())
			throw new DTException("Category is not persistent");
		this.categoryId = category.getId();
		this.name = name;
	}

	public AttributeTypeImpl(long categoryId, String name) {
		this.categoryId = category.getId();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public long getCategoryId() {
		return categoryId;
	}

	@Override
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

}
