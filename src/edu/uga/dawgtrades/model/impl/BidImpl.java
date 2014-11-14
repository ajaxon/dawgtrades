package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.RegisteredUser;

public class BidImpl extends Persistent implements Bid {
	
	private long auctionId;
	private long registeredUserId;
	private float price;
	private Date date;

	public BidImpl(Auction auction, RegisteredUser user, float price) {
		auctionId = auction.getId();
		registeredUserId = user.getId();
		this.price = price;
		
	}

	public BidImpl(long auctionId, long registeredUserId, float price) {
		this.auctionId = auctionId;
		this.registeredUserId = registeredUserId;
		this.price = price;
		
	}

	@Override
	public float getAmount() {
		return price;
	}

	@Override
	public void setAmount(float amount) {
		price = amount;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
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
