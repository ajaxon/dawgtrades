package edu.uga.dawgtrades.persist.impl;

import java.util.Iterator;
import java.sql.ResultSet;
import java.util.NoSuchElementException;
import java.sql.Connection;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.RegisteredUser;

public class BidIterator implements Iterator<Bid> {

	private ResultSet rs = null;
	private ObjectModel objectModel = null;
	private boolean more = false;

	public BidIterator(ResultSet rs, ObjectModel objectModel) throws Exception{
		this.rs = rs;
		this.objectModel = objectModel;
		try{
			more = rs.next();
		}catch(Exception e){
			throw new Exception("BidIterator: Cannot create an iterator." + e);
		}
	}

	@Override
	public boolean hasNext() {
		return more;
	}

	@Override
	public Bid next() {
		long id;
		long auctionId;
		long registeredUserId;
		long price;
		Bid bid = null;
		if(more){
			try{
				id = rs.getLong(1);
				auctionId = rs.getLong(2);
				registeredUserId = rs.getLong(3);
				price = rs.getLong(4);
			}catch(Exception e){
				throw new NoSuchElementException("BidIterator: No next object; root cause " + e);
			}
			try{
				Auction auction = objectModel.createAuction(null, 0, null);
				RegisteredUser registeredUser = objectModel.createRegisteredUser(null, null, null, null, false, null, null, false);
				auction.setId(auctionId);
				registeredUser.setId(registeredUserId);
				bid = objectModel.createBid(auction, registeredUser, price);
				bid.setId(id);
				bid.setAmount(price);
			}catch(Exception e){}
			return bid;
		}else{
			throw new NoSuchElementException("ExperienceReportIterator: No next object");
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
