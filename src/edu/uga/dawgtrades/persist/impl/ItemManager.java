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
		
	    String insertItemSql = "insert into item(categoryId, userId, identifier, name, description) values (?, ?, ?, ?, ?, ?, ?,?)";
	    String updateItemSql ="update item set categoryId = ?, userId = ?, identifier = ?, name = ?, description = ? where id = ?";
	    PreparedStatement stmt;
	    int inscnt;
	    long itemId;
	
	
	
	    try {
	
	        if( !item.isPersistent() )
	            stmt = (PreparedStatement) conn.prepareStatement( insertItemSql );
	        else
	            stmt = (PreparedStatement) conn.prepareStatement( updateItemSql );
	
	        if( item.getCategoryId() != null )
	            stmt.setLong( 1, item.getCategoryId() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: categoryId is undefined" );
	
	        if( item.getUserId() != null )
	            stmt.setLong( 2, item.getUserId() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: userID undefined" );
	
	        if( item.getIdentifier() != null )
	            stmt.setString( 3,  item.getIdentifier() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: identifier undefined" );
	
	        if( item.getName() != null )
	            stmt.setString( 4, item.getName() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: name undefined" );
	
	        if( item.getDescription() != null )
	            stmt.setString( 5, item.getDescription() );
	        else
	       
	        if( item.isPersistent() )
	            stmt.setLong( 6, item.getId() );
	
	        inscnt = stmt.executeUpdate();
	
	        if( !item.isPersistent() ) {
	            // in case this this object is stored for the first time,
	            // we need to establish its persistent identifier (primary key)
	            if( inscnt == 1 ) {
	                String sql = "select last_insert_id()";
	                if( stmt.execute( sql ) ) { // statement returned a result
	                    // retrieve the result
	                    ResultSet r = stmt.getResultSet();
	                    // we will use only the first row!
	                    while( r.next() ) {
	                        // retrieve the last insert auto_increment value
	                        itemId = r.getLong( 1 );
	                        if( itemId > 0 )
	                            item.setId( itemId ); // set this person's db id (proxy object)
	                    }
	                }
	            }
	        }
	        else {
	            if( inscnt < 1 )
	                throw new DTException( "ItemManager.save: failed to save an Item" );
	        }
	    }
	    catch( SQLException e ) {
	        e.printStackTrace();
	        throw new DTException( "ItemManager.save: failed to save a Item: " + e );
	    }
		
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
