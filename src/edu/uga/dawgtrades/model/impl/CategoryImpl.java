package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;

public class CategoryImpl extends Persistent implements Category {

	private long parentId;
	private String name;
	
	public CategoryImpl(Category parent, String name) {
		
		super(-1);
		if(parent == null)
			throw new DTException("Parent is null");
		if (!parent.isPersistent())
			throw new DTException("Parent is not persistent");
		this.parentId=parent.getId();
		this.name=name;
	}
	
	public CategoryImpl(long parentId,String name){
		super(-1);
		this.parentId=parentId;
		this.name=name;
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
	public long getParentId() {
		return this.parentId;
	}

	@Override
	public void setParentId(long parentId) {
		this.parentId=parentId;
	}
}
