package edu.uga.dawgtrades.persist.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AuctionIterator implements Iterator<Auction> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;


    public AuctionIterator(ResultSet rs, ObjectModel objectModel) throws DTException{

        this.rs=rs;
        this.objectModel=objectModel;
        try{
            more = rs.next();

        }catch(Exception e){

            throw new DTException("AuctionIterator");

        }
    }


    @Override
    public boolean hasNext() {

        return more;
    }

    @Override
    public Auction next() {

        long id;
        Date expiration;
        float minPrice;
        long itemId;

        if(more){
            try{
                id =rs.getLong(1);
                expiration = rs.getDate(2);
                minPrice = rs.getFloat(3);
                itemId = rs.getLong(4);

                more = rs.next();

            }catch(Exception e){

                throw new NoSuchElementException("AuctionIterator:No next Auction object");

            }

            Auction auction = null;
            auction = objectModel.createAuction();
            auction.setId(id);
            auction.setExpiration(expiration);
            auction.setMinPrice(minPrice);
            auction.setItemId(itemId);

            return auction;





        }else{

            throw new NoSuchElementException("AuctionIterator:No such element");

        }
    }
    @Override
    public void remove() {

        throw new UnsupportedOperationException();
    }

}