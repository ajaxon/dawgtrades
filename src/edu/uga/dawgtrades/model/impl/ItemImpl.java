package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ItemImpl extends Persistent implements Item {

	public ItemImpl(Category category, RegisteredUser user, String identifier,
			String name, String description) {

	
	}
	
	public ItemImpl(Category category, long userID, String identifier,
			String name, String description) {

	
	}
	
	

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdentifier(String identifier) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getCategoryId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCategoryId(long categoryId) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getOwnerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setOwnerId(long ownerId) {
		// TODO Auto-generated method stub

	}

}
