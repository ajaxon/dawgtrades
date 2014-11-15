package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.sql.Statement;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
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

		String insertRegisteredUserSql = "insert into registered_user(name,firstName,lastName,email,password,canText)";
		String updateRegisteredUserSql ="";
		PreparedStatement stmt;

	}
	
	public Iterator<RegisteredUser> restore(RegisteredUser registeredUser) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(RegisteredUser registeredUser) throws DTException{
		
		
	}


	public Iterator<Item> restoreItemBy(RegisteredUser registeredUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
