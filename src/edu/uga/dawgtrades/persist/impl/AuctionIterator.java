package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AuctionIterator implements Iterator<Auction> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;


    public AuctionIterator(ResultSet rs, ObjectModel objectModel) throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try{
            more = rs.next();
        }catch( Exception e){
            throw new DTException("AuctionIterator: Cannot create Auction" + e);
        }
    }
	@Override
	public boolean hasNext() {
		return more;
	}

	@Override
	public Auction next() {
        return null;
	}

	@Override
	public void remove() {
        throw new UnsupportedOperationException();
		
	}

}
