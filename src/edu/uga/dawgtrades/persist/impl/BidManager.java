package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class BidManager {


	   private ObjectModel objectModel = null;
	   private Connection  conn = null;
	
	   public BidManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}
	
	 
	


	
	public void save(Bid bid) throws DTException{
		
		
	}
	
	public Iterator<Bid> restore(Bid bid) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Bid bid) throws DTException{
		
		
	}


}
