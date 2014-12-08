package edu.uga.dawgtrades.persist.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
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
        java.util.Date expiration;
        float minPrice;
        long itemId;

        if(more){
            try{
                id =rs.getLong(1);
                System.out.println(rs.getTimestamp(2).toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                expiration = sdf.parse(rs.getTimestamp(2).toString()+"00");
                System.out.println(expiration.toString());
                minPrice = rs.getFloat(3);
                itemId = rs.getLong(4);

                more = rs.next();

            }catch(Exception e){

                throw new NoSuchElementException("AuctionIterator:No next Auction object");

            }

            Auction auction = null;
            auction = objectModel.createAuction();
            auction.setId(id);
            java.util.Date javaDate = expiration;
            System.out.println(javaDate);
            auction.setExpiration(javaDate);
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