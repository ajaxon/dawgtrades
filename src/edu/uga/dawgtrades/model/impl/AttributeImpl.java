package edu.uga.dawgtrades.model.impl;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Item;

public class AttributeImpl extends Persistent implements Attribute {
	
	private long attributeTypeId;
	private long itemId;
	private String value;

	public AttributeImpl(AttributeType attributeType, Item item, String value) throws DTException {
		super(-1);
		if(attributeType == null)
			throw new DTException("Attribute Type is null");
		if(!attributeType.isPersistent())
			throw new DTException("Attribute Type is not persistent");
		if(item == null)
			throw new DTException("Item is null");
		if(!item.isPersistent())
			throw new DTException("Item is not persistent");
		this.attributeTypeId = attributeType.getId();
		this.itemId = item.getId();
		this.value = value;
	}

	public AttributeImpl(long attributeTypeId, long itemId, String value) {
		this.attributeTypeId = attributeTypeId;
		this.itemId = itemId;
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public long getItemId() {
		return itemId;
	}

	@Override
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	@Override
	public long getAttributeType() {
		return attributeTypeId;
	}

	@Override
	public void setAttributeType(long attributeId) {
		this.attributeTypeId = attributeId;
	}

}
