package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public AttributeManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(Attribute attribute) throws DTException{
		
		
	}
	
	public Iterator<Attribute> restore(Attribute attribute) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Attribute attribute) throws DTException{
		
		
	}
	
	
}
