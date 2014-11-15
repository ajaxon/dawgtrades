package edu.uga.dawgtrades.model.impl;

import java.util.Date;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Item;

public class AuctionImpl extends Persistent implements Auction {
	
	private long itemId;
	private float minPrice;
	private Date expiration;

	public AuctionImpl(Item item, float minPrice, Date expiration) {
		if(item == null)
			throw new DTException("Item is null");
		if(!item.isPersistent())
			throw new DTException("Item is not persistent");
		itemId = item.getId();
		this.minPrice = minPrice;
		this.expiration = expiration;
	}
	
	public AuctionImpl(long itemId, float minPrice, Date expiration ){
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
	}

	@Override
	public boolean getIsClosed() {
		Date date = new Date();
		//date is SysDate
		if(date.after(this.expiration)){
			
			return true;
			
		}else{
			
			return false;
		}
	
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
