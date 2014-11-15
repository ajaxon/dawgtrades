package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.sql.Statement;

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

	
	public void save(RegisteredUser registeredUser) throws DTException{

		String insertRegisteredUserSql = "insert into registered_user(name,firstName,lastName,email,password,canText)";
		String updateRegisteredUserSql ="";
		PreparedStatement stmt;

	}
	
	public Iterator<RegisteredUser> restore(RegisteredUser registeredUser) throws DTException{
		String       selectRegisteredUserSql = "select id, name, firstName, lastName, password, email, phone, canText, isAdmin";              
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
	                query.append( " where username = '" + registeredUser.getName() + "'" );
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
	
	                if( registeredUser.getIsAdmin() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " isAdmin = '" + registeredUser.getIsAdmin() + "'" );
	                }   
	                
	                 if( registeredUser.getCanText() != null ) {
	                    if( condition.length() > 0 )
	                        condition.append( " and" );
	                    condition.append( " canText = '" + registeredUser.getCanText() + "'" );
	                }
	
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
		
		
	}


	public Iterator<Item> restoreItemBy(RegisteredUser registeredUser) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
