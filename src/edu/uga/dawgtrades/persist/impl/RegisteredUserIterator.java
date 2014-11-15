package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class RegisteredUserIterator implements Iterator<RegisteredUser> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;


    public RegisteredUserIterator(ResultSet rs, ObjectModel objectModel) throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try{
            more = rs.next();
        }catch( Exception e){
            throw new DTException("RegisteredUserIterator: Cannot create User" + e);
        }
    }
    @Override
	public boolean hasNext() {
		return more;
	}

	@Override
	public RegisteredUser next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
        throw new UnsupportedOperationException();
		
	}

}
