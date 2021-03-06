package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
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
		
	    String insertItemSql = "insert into item(category_id, owner_id, identifier, name, description) values (?, ?, ?, ?, ?)";
	    String updateItemSql ="update item set category_id = ?, owner_id = ?, identifier = ?, name = ?, description = ? where id = ?";
	    PreparedStatement stmt;
	    int inscnt;
	    long itemId;

	
	    try {
	
	        if( !item.isPersistent() )
	            stmt = (PreparedStatement) conn.prepareStatement( insertItemSql );
	        else
	            stmt = (PreparedStatement) conn.prepareStatement( updateItemSql );
	
	        if( item.getCategoryId() >= 0 )
	            stmt.setLong( 1, item.getCategoryId() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: categoryId is less than 0" );
	
	        if( item.getOwnerId() >= 0 )
	            stmt.setLong( 2, item.getOwnerId() );
	        else
	            throw new DTException( "ItemManager.save: can't save a Item: userID is less than 0" );
	
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
	        	throw new DTException("ItemManager.save: can't save an Item:description undefined");
	       
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
		
		
		String       selectItemSql = "select id, name, identifier, description, owner_id, category_id from item";
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	
	        condition.setLength( 0 );
	        
	        // form the query based on the given Item object instance
	        query.append( selectItemSql );
	        
	        if( item != null ) {
	            if( item.getId() >= 0 ) // id is unique, so it is sufficient to get an item
	                query.append( " where id = " + item.getId() );
	            else if( item.getIdentifier() != null ) // identifier is unique, so it is sufficient to get an item
	                query.append( " where identifier = '" + item.getIdentifier() + "'" );
	            else {
	                if( item.getCategoryId() >= 0 )
	                    condition.append( " category_id = '" + item.getCategoryId() + "'" );
	
	                if( item.getOwnerId() >= 0 ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " owner_id = '" + item.getOwnerId() + "'" );
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


	public Category restoreCategoryBy(Item item) throws DTException {
		String       selectItemSql = "select c.id, c.name, c.parent_id from item p, category c where p.category_id = c.id";
		Statement stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Item object instance
		query.append( selectItemSql );

		if( item != null ) {
			if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
				query.append( " and p.id = " + item.getId() );
			else if( item.getIdentifier() != null ) // identifier is unique, so it is sufficient to get a item
				query.append( " and p.identifier = '" + item.getIdentifier() + "'" );
			else {
				if (item.getCategoryId() >= 0)
					condition.append(" p.category_id = '" + item.getCategoryId() + "'");

				if (item.getOwnerId() >= 0) {
					if (condition.length() == 0)
						condition.append(" p.owner_id = '" + item.getOwnerId() + "'");
					else
						condition.append(" AND p.owner_id = '" + item.getOwnerId() + "'");
				}

				if (item.getName() != null) {
					if (condition.length() == 0)
						condition.append(" p.name = '" + item.getName() + "'");
					else
						condition.append(" AND p.name = '" + item.getName() + "'");
				}

				if( item.getDescription() != null ) {
					if(condition.length() == 0)
						condition.append(" p.description = '" + item.getDescription() + "'");
					else
						condition.append(" AND p.description = '" + item.getDescription() + "'");
				}

				if( condition.length() > 0 ) {
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
				Iterator<Category> catIter = new CategoryIterator( r, objectModel );
				if( catIter != null && catIter.hasNext() ){
					return catIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent Category objects; Root cause: " + e );
		}

		throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent Category objects" );
	}


	public Iterator<Attribute> restoreAttributeBy(Item item) throws DTException {
		String       selectItemSql = "select c.id, c.value, c.attribute_type_id, c.item_id from item p, attribute c where c.item_id = p.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Item object instance
		query.append( selectItemSql );

		if( item != null ) {
			if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
				query.append( " and p.id = " + item.getId() );
			else if( item.getIdentifier() != null ) // identifier is unique, so it is sufficient to get a item
				query.append( " and p.identifier = '" + item.getIdentifier() + "'" );
			else {
				if( item.getCategoryId() >= 0 )
					condition.append( " p.category_id = '" + item.getCategoryId() + "'" );

				if( item.getOwnerId() >= 0 ) {
					if(condition.length() == 0)
						condition.append(" p.owner_id = '" + item.getOwnerId() + "'");
					else
						condition.append(" AND p.owner_id = '" + item.getOwnerId() + "'");
				}

				if( item.getName() != null) {
					if(condition.length() == 0)
						condition.append(" p.name = '" + item.getName() + "'");
					else
						condition.append(" AND p.name = '" + item.getName() + "'");
				}

				if( item.getDescription() != null ) {
					if(condition.length() == 0 )
						condition.append(" p.description = '" + item.getDescription() + "'");
					else
						condition.append(" AND p.description = '" + item.getDescription() + "'");
				}
				if( condition.length() > 0 ) {
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
				return new AttributeIterator( r, objectModel );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent Category objects; Root cause: " + e );
		}

		throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent Category objects" );
	}


	public RegisteredUser restoreRegisteredUserBy(Item item) throws DTException {
	    String       selectItemSql = "select c.id, c.name, c.firstName, c.lastName, c.password, c.email, c.phone, c.canText, c.isAdmin from item p, user c where p.owner_id = c.id";
	    Statement    stmt = null;
	    StringBuffer query = new StringBuffer( 100 );
	    StringBuffer condition = new StringBuffer( 100 );
	
	    condition.setLength( 0 );
	    
	    // form the query based on the given Item object instance
	    query.append( selectItemSql );
	    
	    if( item != null ) {
	        if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
	            query.append( " and p.id = " + item.getId() );
	        else if( item.getIdentifier() != null ) // identifier is unique, so it is sufficient to get a item
	            query.append( " and p.identifier = '" + item.getIdentifier() + "'" );
	        else {
	            if( item.getCategoryId() >= 0 )
	                condition.append( " p.category_id = '" + item.getCategoryId() + "'" );
	
	            if( item.getOwnerId() >= 0 ) {
					if(condition.length() == 0)
						condition.append(" p.owner_id = '" + item.getOwnerId() + "'");
					else
						condition.append(" AND p.owner_id = '" + item.getOwnerId() + "'");
				}
	
	            if( item.getName() != null ) {
					if (condition.length() == 0)
						condition.append(" p.name = '" + item.getName() + "'");
					else
						condition.append(" AND p.name = '" + item.getName() + "'");
				}

	            if( item.getDescription() != null && condition.length() == 0 ) {
					if (condition.length() == 0)
						condition.append(" p.description = '" + item.getDescription() + "'");
					else
						condition.append(" AND p.description = '" + item.getDescription() + "'");
				}

	            if( condition.length() > 0 ) {
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
	            Iterator<RegisteredUser> rUIter = new RegisteredUserIterator( r, objectModel );
				if( rUIter != null && rUIter.hasNext() ){
					return rUIter.next();
				}
				else
					return null;
	        }
	    }
	    catch( Exception e ) {      // just in case...
	        throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent RegisteredUser objects; Root cause: " + e );
	    }
	
	    throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent RegisteredUser objects" );
	}


	public Auction restoreAuctionBy(Item item) throws DTException {
		String       selectItemSql = "select c.id, c.expiration, c.minPrice, c.item_id from item p, auction c where c.item_id = p.id";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Item object instance
		query.append( selectItemSql );

		if( item != null ) {
			if( item.getId() >= 0 ) // id is unique, so it is sufficient to get a item
				query.append( " and p.id = " + item.getId() );
			else if( item.getIdentifier() != null ) // identifier is unique, so it is sufficient to get a item
				query.append( " and p.identifier = '" + item.getIdentifier() + "'" );
			else {
				if( item.getCategoryId() >= 0 )
					condition.append( " p.category_id = '" + item.getCategoryId() + "'" );

				if( item.getOwnerId() >= 0 ) {
					if (condition.length() == 0)
						condition.append(" p.owner_id = '" + item.getOwnerId() + "'");
					else
						condition.append(" AND p.owner_id = '" + item.getOwnerId() + "'");
				}

				if( item.getName() != null ) {
					if (condition.length() == 0)
						condition.append(" p.name = '" + item.getName() + "'");
					else
						condition.append(" AND p.name = '" + item.getName() + "'");
				}

				if( item.getDescription() != null ) {
					if(condition.length() == 0 )
						condition.append(" p.description = '" + item.getDescription() + "'");
					else
						condition.append(" AND p.description = '" + item.getDescription() + "'");
				}

				if( condition.length() > 0 ) {
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
				Iterator<Auction> aucIter = new AuctionIterator( r, objectModel );
				if( aucIter != null && aucIter.hasNext() ){
					return aucIter.next();
				}
				else
					return null;
			}
		}
		catch( Exception e ) {      // just in case...
			throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent RegisteredUser objects; Root cause: " + e );
		}

		throw new DTException( "ItemManager.restoreEstablishedBy: Could not restore persistent RegisteredUser objects" );
	}
	
	
	

}
