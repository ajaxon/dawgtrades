package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class CategoryManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public CategoryManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(Category category) throws DTException{
		
		
	}
	
	public Iterator<Category> restore(Category category) throws DTException{
		
		
		return null;
		
		
	}
	
	public void delete(Category category) throws DTException{
		
		
	}
	

}
