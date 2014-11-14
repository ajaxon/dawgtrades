package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeTypeManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	public AttributeTypeManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}
	
	 
	


	
	public void save(AttributeType attributeType) throws DTException{
		
		
	}
	
	public Iterator<AttributeType> restore(AttributeType attributeType) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(AttributeType attributeType) throws DTException{
		
		
	}






	public Category restoreCategoryBy(AttributeType attributeType) {
		// TODO Auto-generated method stub
		return null;
	}

}
