package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ItemImpl;

public class ItemIterator implements Iterator<Item> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;


    public ItemIterator(ResultSet rs, ObjectModel objectModel) throws DTException
    {
        this.rs = rs;
        this.objectModel = objectModel;
        try{
            more = rs.next();
        }catch( Exception e){
            throw new DTException("ItemIterator: Cannot create Item" + e);
        }
    }
    @Override
    public boolean hasNext() {
        return more;
    }

    @Override
    public Item next() {
        
        
		    long   id;
		    String name;
		    String identifier;
		    String description;
		    long owner_id;
		    long category_id;
		
		    if( more ) {
		
		        try {
		            id = rs.getLong( 1 );
		            name = rs.getString( 2 );
		            identifier = rs.getString( 3 );
		            description = rs.getString( 4 );
		            owner_id = rs.getLong( 5 );
		            category_id = rs.getLong( 6 );
		
		            more = rs.next();
		        }
		        catch( Exception e ) {	// just in case...
		            throw new NoSuchElementException( "ItemIterator: No next Item object; root cause: " + e );
		        }

                //Item item = new ItemImpl(category_id,owner_id,identifier,name,description);
                Item item = objectModel.createItem();
                item.setIdentifier(identifier);
                item.setOwnerId(owner_id);
                item.setDescription(description);
                item.setCategoryId(category_id);
                item.setName(name);

                item.setId( id );
		        
		        return item;
		    }
		    else {
		        throw new NoSuchElementException( "RegisteredUserIterator: No next RegisteredUser object" );
		    }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();

    }

}
