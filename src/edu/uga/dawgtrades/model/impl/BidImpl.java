package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

public class BidImpl extends Persistent implements Bid {
	
	private long auctionId;
	private long registeredUserId;
	private float amount;
	private Date date;
    RegisteredUser user = null;
    Auction auction = null;

	public BidImpl(Auction auction, RegisteredUser user, float amount) throws DTException {
		super(-1);
		if(auction == null)
			throw new DTException("Auction is null");
		if(!auction.isPersistent())
			throw new DTException("Auction is not persistent");
		if(user == null)
			throw new DTException("User is null");
		if(!user.isPersistent())
			throw new DTException("User is not persistent");
		auctionId = auction.getId();
		registeredUserId = user.getId();
		this.amount = amount;
		this.date =new Date();
        this.user = user;
        this.auction = auction;
	}

	public BidImpl(long auctionId, long registeredUserId, float amount) {
		this.auctionId = auctionId;
		this.registeredUserId = registeredUserId;
		this.amount = amount;

	}

	@Override
	public float getAmount() {
		return amount;
	}

	@Override
	public void setAmount(float amount) {
		this.amount = amount;
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
			Auction auction = this.getAuction();
        return (auction.getMinPrice() == this.getAmount());
	}

	@Override
	public Auction getAuction() {
		return this.auction;
	}

	@Override
	public RegisteredUser getRegisteredUser() {

        return user;
	}

}
