package edu.uga.dawgtrades.model.impl;

import java.util.Date;
import java.util.ArrayList;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

public class AuctionImpl extends Persistent implements Auction {
	
	private long itemId;
	private float minPrice;
	private Date expiration;


    private ArrayList<Bid> bids;

	public AuctionImpl(Item item, float minPrice, Date expiration) throws DTException {
        super(-1);
		if(item == null)
			throw new DTException("Item is null");
		if(!item.isPersistent())
			throw new DTException("Item is not persistent");
		this.itemId = item.getId();
		this.minPrice = minPrice;
		this.expiration = expiration;
	}
	
	public AuctionImpl(long itemId, float minPrice, Date expiration ){
        super(-1);
		this.itemId = itemId;
		this.minPrice = minPrice;
		this.expiration = expiration;
	}

	@Override
	public float getMinPrice() {
		return minPrice;
	}

	@Override
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	@Override
	public Date getExpiration() {
		return expiration;
	}

	@Override
	public void setExpiration(Date expiration) {
	
            this.expiration = expiration;
        // invalid expiration date does not get set
	}

	@Override
	public boolean getIsClosed() {
		Date now = new Date();
        return (now.after(this.expiration)) ? true : false;
	
	}

	@Override
	public float getSellingPrice() {
		if(getIsClosed())
			return minPrice;
		else
			return -1;
	}

	@Override
	public long getItemId() {
		return itemId;
	}

	@Override
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
}

