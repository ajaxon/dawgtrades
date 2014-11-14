package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;

public class CategoryImpl extends Persistent implements Category {

	private long parentId;
	private Category parent;
	private String name;
	
	public CategoryImpl(Category parent, String name) {
		
		super(-1);
		this.setParent(parent);
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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

}
