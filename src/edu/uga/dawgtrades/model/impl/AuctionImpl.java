package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;

public class AuctionImpl extends Persistent implements Auction {

	@Override
	public float getMinPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMinPrice(float minPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date getExpiration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExpiration(Date expiration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getIsClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float getSellingPrice() {
		// TODO Auto-generated method stub
		return 0;
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
}
