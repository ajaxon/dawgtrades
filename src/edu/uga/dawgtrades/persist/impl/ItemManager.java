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
		
		
		String       selectItemSql = "select id, categoryId, userId, identifier, name, description from item";
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	
	        condition.setLength( 0 );
	        
	        // form the query based on the given Item object instance
	        query.append( selectItemSql );
	        
	        if( item != null ) {
	            if( item.getId() >= 0 ) // id is unique, so it is sufficient to get an item
	                query.append( " where id = " + item.getId() );
	            else if( item.identifier() != null ) // identifier is unique, so it is sufficient to get an item
	                query.append( " where identifier = '" + item.getIdentifier() + "'" );
	            else {
	                if( item.getCategoryId() != null )
	                    condition.append( " categoryId = '" + item.getCategoryId() + "'" );
	
	                if( item.getUserId != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " userId = '" + item.getUserId() + "'" );
	                }
	
	                if( item.getName() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " name = '" + item.getName() + "'" );
	                }
	
	                if( item.getDescription() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " description = '" + item.getDescription() + "'" );
	                }
	
	                if( condition.length() > 0 ) {
	                    query.append(  " where " );
	                    query.append( condition );
	                }
	            }
	        }
	        
	        try {
	
	            stmt = conn.createStatement();
	
	            // retrieve the persistent Item object
	            //
	            if( stmt.execute( query.toString() ) ) { // statement returned a result
	                ResultSet r = stmt.getResultSet();
	                return new ItemIterator( r, objectModel );
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new DTException( "ItemManager.restore: Could not restore persistent Item object; Root cause: " + e );
          }
        
          // if we get to this point, it's an error
	  throw new DTException( "Item.restore: Could not restore persistent Item object" );
		
		
	}
	
	public void delete(Item item) throws DTException{

		String               deleteItemSql = "delete from item where id = ?";              
		PreparedStatement    stmt = null;
		int                  inscnt;
		    
		// form the query based on the given item object instance
		if( !item.isPersistent() ) // is the item object persistent?  If not, nothing to actually delete
		    return;
		    
		try {
		        
		    //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
		    //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
		    stmt = (PreparedStatement) conn.prepareStatement( deleteItemSql );
		        
		    stmt.setLong( 1, item.getId() );
		        
		    inscnt = stmt.executeUpdate();
		        
		    if( inscnt == 0 ) {
		        throw new DTException( "itemManager.delete: failed to delete this item" );
		    }
		}
		catch( SQLException e ) {
		    throw new DTException( "Item.delete: failed to delete this Item: " + e.getMessage() );
		}
		
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
