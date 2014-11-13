package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;

public class MembershipManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public MembershipManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(Membership membership) throws DTException{
		
		
	}
	
	public Iterator<Membership> restore(Membership membership) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Membership membership) throws DTException{
		
		
	}
	

}
