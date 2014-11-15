package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class ItemManager {

	 private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	
	public ItemManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(Item item) throws DTException{
		
		
	}
	
	public Iterator<Item> restore(Item item) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Item item) throws DTException{
		
		
	}


	public Category restoreCategoryBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	public Iterator<Attribute> restoreAttributeBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	public RegisteredUser restoreRegisteredUserBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}


	public Auction restoreAuctionBy(Item item) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
