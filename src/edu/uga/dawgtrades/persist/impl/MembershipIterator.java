package edu.uga.dawgtrades.persist.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;

public class MembershipIterator implements Iterator<Membership> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more;

    public MembershipIterator(ResultSet r, ObjectModel objectModel) throws DTException {

        this.rs=r;
        this.objectModel=objectModel;
        try{

            more = rs.next();

        }catch(Exception e){
            throw new DTException("AttributeIterator"+ e);

        }

    }

    @Override
    public boolean hasNext() {

        return more;
    }

    @Override
    public Membership next() {
        long id;
        float price;
        Date date;
        Membership membership = null;
        if(more){

            try{
                id = rs.getLong(1);
                price = rs.getFloat(2);
                date = rs.getDate(3);

                more = rs.next();

            }catch(Exception e){

                throw new NoSuchElementException("MembershipIterator.next"+e);

            }

            try {

                membership = objectModel.createMembership(price, date);
                membership.setId(id);

            } catch (DTException e) {

                e.printStackTrace();
            }



        }
        return membership;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();


    }

}