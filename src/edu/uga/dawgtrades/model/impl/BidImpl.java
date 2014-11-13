package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.RegisteredUser;

public class BidImpl extends Persistent implements Bid {

	@Override
	public float getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAmount(float amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(Date date) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWinning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Auction getAuction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisteredUser getRegisteredUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
