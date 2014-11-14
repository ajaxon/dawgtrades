package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Item;

public class AttributeImpl extends Persistent implements Attribute {

	public AttributeImpl(AttributeType attributeType, Item item, String value) {
		// TODO Auto-generated constructor stub
	}

	public AttributeImpl(long attributeTypeId, long itemId, String value) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setId(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPersistent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getItemId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setItemId(long itemId) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getAttributeType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAttributeType(long attributeId) {
		// TODO Auto-generated method stub

	}

}
