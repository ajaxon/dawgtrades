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
	public RegisteredUser next() throws DTException {

		    long   id;
		    String name;
		    String firstName;
		    String lastName;
		    String password;
		    String email;
		    String phone;
		    boolean canText;
		    boolean isAdmin;
		
		    if( more ) {
		
		        try {
		            id = rs.getLong( 1 );
		            name = rs.getString( 2 );
		            firstName = rs.getString( 3 );
		            lastName = rs.getString( 4 );
		            password = rs.getString( 5 );
		            email = rs.getString( 6 );
		            phone = rs.getString( 7 );
		            canText = rs.getBoolean( 8 );
		            isAdmin = rs.getBoolean( 9 );
		
		            more = rs.next();
		        }
		        catch( Exception e ) {	// just in case...
		            throw new DTException( "RegisteredUserIterator: No next RegisteredUser object; root cause: " + e );
		        }
		        
		        RegisteredUser registeredUser = objectModel.createRegisteredUser( name, firstName, lastName, password, email, phone, canText, isAdmin );
		        registeredUser.setId( id );
		        
		        return registeredUser;
		    }
		    else {
		        throw new DTException( "RegisteredUserIterator: No next RegisteredUser object" );
		    }
		}

	}

	@Override
	public void remove() {
        throw new UnsupportedOperationException();
		
	}

}
