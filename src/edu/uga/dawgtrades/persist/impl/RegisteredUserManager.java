package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class RegisteredUserManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public RegisteredUserManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(RegisteredUser registeredUser) throws DTException{
		
		
	}
	
	public Iterator<RegisteredUser> restore(RegisteredUser registeredUser) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(RegisteredUser registeredUser) throws DTException{
		
		
	}
	
}
