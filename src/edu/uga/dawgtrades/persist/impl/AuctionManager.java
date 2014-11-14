package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class AuctionManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	    public AuctionManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}
	
	 
	


	
	public void save(Auction auction) throws DTException{
		
		
	}
	
	public Iterator<Auction> restore(Auction auction) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Auction auction) throws DTException{
		
		
	}






	public Item restoreItemBy(Auction auction) {
		// TODO Auto-generated method stub
		return null;
	}
}
