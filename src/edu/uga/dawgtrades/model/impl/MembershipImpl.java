package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Membership;

public class MembershipImpl extends Persistent implements Membership {
	private float price;
	private Date date;
	@Override
	public float getPrice() {
		return price;
	}

	@Override
	public void setPrice(float price) {
		this.price = price;

	}

	@Override
	public Date getDate() {
		return date;
	}

}
