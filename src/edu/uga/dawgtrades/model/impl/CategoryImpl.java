package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;

public class CategoryImpl extends Persistent implements Category {

	private long parentId;
	private String name;
	
	public CategoryImpl(Category parent, String name) throws DTException {
		
		super(-1);
		if(parent != null)
        {
            if (!parent.isPersistent())
                throw new DTException("Parent is not persistent");
            else
                this.parentId = parent.getId();
        }
		else
	        this.parentId=0;
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

    public String toString(){
        return this.getName();
    }
}
