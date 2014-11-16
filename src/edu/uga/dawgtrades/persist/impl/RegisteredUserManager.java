package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class RegisteredUserManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public RegisteredUserManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(RegisteredUser user) throws DTException {

		String insertRegisteredUserSql = "insert into user(name,firstName,lastName,email,password,phone,canText, isAdmin) values (?, ?, ?, ?, ?, ?, ?,?)";
		String updateRegisteredUserSql ="update user set name = ?, firstName = ?, lastName = ?, email = ?, password = ?, phone = ?,canText = ?, isAdmin = ?, id = ?";
		PreparedStatement stmt;
        int inscnt;
        long userId;



        try {

            if( !user.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertRegisteredUserSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateRegisteredUserSql );

            if( user.getName() != null ) // clubsuser is unique, so it is sufficient to get a person
                stmt.setString( 1, user.getName() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a RegisteredUser: userName undefined" );

            if( user.getFirstName() != null )
                stmt.setString( 2, user.getFirstName() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a RegisteredUser: firstName undefined" );

            if( user.getLastName() != null )
                stmt.setString( 3,  user.getLastName() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a RegisteredUser: email undefined" );

            if( user.getEmail() != null )
                stmt.setString( 4, user.getEmail() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a RegisteredUser: first name undefined" );

            if( user.getPassword() != null )
                stmt.setString( 5, user.getPassword() );
            else
                throw new DTException( "RegisteredUserManager.save: can't save a RegisteredUser: last name undefined" );
            if(user.getPhone() != null)
                stmt.setString(6,user.getPhone());
            else
                stmt.setNull(6, Types.VARCHAR);

            stmt.setBoolean( 7, user.getCanText() );
            stmt.setBoolean(8,user.getIsAdmin());



            if( user.isPersistent() )
                stmt.setLong( 9, user.getId() );

            inscnt = stmt.executeUpdate();

            if( !user.isPersistent() ) {
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
                            userId = r.getLong( 1 );
                            if( userId > 0 )
                                user.setId( userId ); // set this person's db id (proxy object)
                        }
                    }
                }
            }
            else {
                if( inscnt < 1 )
                    throw new DTException( "RegisteredUserManager.save: failed to save a User" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new DTException( "RegisteredUserManager.save: failed to save a User: " + e );
        }
    }


	
	public Iterator<RegisteredUser> restore(RegisteredUser registeredUser) throws DTException{
		String       selectRegisteredUserSql = "select id, name, firstName, lastName, password, email, phone, canText, isAdmin from user";
	        Statement    stmt = null;
	        StringBuffer query = new StringBuffer( 100 );
	        StringBuffer condition = new StringBuffer( 100 );
	
	        condition.setLength( 0 );
	        
	        // form the query based on the given RegisteredUser object instance
	        query.append( selectRegisteredUserSql );
	        
	        if( registeredUser != null ) {
	            if( registeredUser.getId() >= 0 ) // id is unique, so it is sufficient to get a registered user
	                query.append( " where id = " + registeredUser.getId() );
	            else if( registeredUser.getName() != null ) // userName is unique, so it is sufficient to get a registered user
	                query.append( " where name = '" + registeredUser.getName() + "'" );
	            else {
	                if( registeredUser.getPassword() != null )
	                    condition.append( " password = '" + registeredUser.getPassword() + "'" );
	
	                if( registeredUser.getEmail() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " email = '" + registeredUser.getEmail() + "'" );
	                }
	
	                if( registeredUser.getFirstName() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " firstName = '" + registeredUser.getFirstName() + "'" );
	                }
	
	                if( registeredUser.getLastName() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " lastName = '" + registeredUser.getLastName() + "'" );
	                }
	
	                if( condition.length() > 0 )
	                    condition.append( " and" );
	                condition.append( " isAdmin = '" + registeredUser.getIsAdmin() + "'" );
	                
	                if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " canText = '" + registeredUser.getCanText() + "'" );
	
	                if( registeredUser.getPhone() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " phone = '" + registeredUser.getPhone() + "'" );
	                }
	
	                if( condition.length() > 0 ) {
	                    query.append(  " where " );
	                    query.append( condition );
	                }
	            }
	        }
	        
	        try {
	
	            stmt = conn.createStatement();
	
	            // retrieve the persistent RegisteredUser object
	            //
	            if( stmt.execute( query.toString() ) ) { // statement returned a result
	                ResultSet r = stmt.getResultSet();
	                return new RegisteredUserIterator( r, objectModel );
	            }
	        }
	        catch( Exception e ) {      // just in case...
	            throw new DTException( "RegisteredUserManager.restore: Could not restore persistent RegisteredUser object; Root cause: " + e );
          }
        
          // if we get to this point, it's an error
	  throw new DTException( "RegisteredUser.restore: Could not restore persistent RegisteredUser object" );
        
	}
	
	
	public void delete(RegisteredUser registeredUser) throws DTException{
		String               deleteRegisteredUserSql = "delete from registeredUser where id = ?";              
		PreparedStatement    stmt = null;
		int                  inscnt;
		    
		// form the query based on the given registeredUser object instance
		if( !registeredUser.isPersistent() ) // is the registeredUser object persistent?  If not, nothing to actually delete
		    return;
		    
		try {
		        
		    //DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
		    //DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
		    stmt = (PreparedStatement) conn.prepareStatement( deleteRegisteredUserSql );
		        
		    stmt.setLong( 1, registeredUser.getId() );
		        
		    inscnt = stmt.executeUpdate();
		        
		    if( inscnt == 0 ) {
		        throw new DTException( "registeredUserManager.delete: failed to delete this registeredUser" );
		    }
		}
		catch( SQLException e ) {
		    throw new DTException( "RegisteredUser.delete: failed to delete this RegisteredUser: " + e.getMessage() );
		}
	}


	public Iterator<Item> restoreItemBy(RegisteredUser registeredUser) throws DTException {
	    String       selectRegisteredUserSql = "select c.id, c.name, c.identifier, c.description, c.owner_id, c.category_id from registeredUser p, item c where c.owner_id = p.id";              
	    Statement    stmt = null;
	    StringBuffer query = new StringBuffer( 100 );
	    StringBuffer condition = new StringBuffer( 100 );
	
	    condition.setLength( 0 );
	    
	    // form the query based on the given RegisteredUser object instance
	    query.append( selectRegisteredUserSql );
	    
	    if( registeredUser != null ) {
	        if( registeredUser.getId() >= 0 ) // id is unique, so it is sufficient to get a registeredUser
	            query.append( " and p.id = " + registeredUser.getId() );
	        else if( registeredUser.getName() != null ) // userName is unique, so it is sufficient to get a registeredUser
	            query.append( " and p.name = '" + registeredUser.getName() + "'" );
	        else {
	            if( registeredUser.getPassword() != null )
	                condition.append( " p.password = '" + registeredUser.getPassword() + "'" );
	
	            if( registeredUser.getEmail() != null && condition.length() == 0 )
	                condition.append( " p.email = '" + registeredUser.getEmail() + "'" );
	            else
	                condition.append( " AND p.email = '" + registeredUser.getEmail() + "'" );
	
	            if( registeredUser.getFirstName() != null && condition.length() == 0 )
	                condition.append( " p.firstname = '" + registeredUser.getFirstName() + "'" );
	            else
	                condition.append( " AND p.firstname = '" + registeredUser.getFirstName() + "'" );
	
	            if( registeredUser.getLastName() != null && condition.length() == 0 )
	                condition.append( " p.lastname = '" + registeredUser.getLastName() + "'" );
	            else
	                condition.append( " AND p.lastname = '" + registeredUser.getLastName() + "'" );
	
	            if( condition.length() == 0 )
	                condition.append( " p.canText = '" + registeredUser.getCanText() + "'" );
	            else
	                condition.append( " AND p.canText = '" + registeredUser.getCanText() + "'" );         
	
	            if( registeredUser.getPhone() != null && condition.length() == 0 )
	                condition.append( " p.phone = '" + registeredUser.getPhone() + "'" );
	            else
	                condition.append( " AND p.phone = '" + registeredUser.getPhone() + "'" );
	            
	            if( condition.length() == 0 )
	                condition.append( " p.isAdmin = '" + registeredUser.getIsAdmin() + "'" );
	            else
	                condition.append( " AND p.isAdmin = '" + registeredUser.getIsAdmin() + "'" ); 
	            
	            if( condition.length() > 0 ) {
	                query.append( condition );
	            }
	        }
	    }
	            
	    try {
	
	        stmt = conn.createStatement();
	
	        // retrieve the persistent RegisteredUser object
	        //
	        if( stmt.execute( query.toString() ) ) { // statement returned a result
	            ResultSet r = stmt.getResultSet();
	            return new ItemIterator( r, objectModel );
	        }
	    }
	    catch( Exception e ) {      // just in case...
	        throw new DTException( "RegisteredUserManager.restoreEstablishedBy: Could not restore persistent Item objects; Root cause: " + e );
	    }
	
	    throw new DTException( "RegisteredUserManager.restoreEstablishedBy: Could not restore persistent Item objects" );
	}
}
