package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;

public class MembershipManager {

	   private ObjectModel objectModel = null;
	    private Connection  conn = null;
	
	public MembershipManager(Connection conn, ObjectModel objectModel) {
		
		   this.conn = conn;
	       this.objectModel = objectModel;
	}

	
	public void save(Membership membership) throws DTException{

		String insertMembershipSql = "insert into membership(price, date) values (?, ?)";
		String updateMembershipSql ="update membership set price = ?, date = ? where id = ?";
		PreparedStatement stmt;
		int inscnt;
		long membershipId;

		try {

			if( !membership.isPersistent() )
				stmt = (PreparedStatement) conn.prepareStatement( insertMembershipSql );
			else
				stmt = (PreparedStatement) conn.prepareStatement( updateMembershipSql );

			if( membership.getPrice() > 0 )
				stmt.setFloat( 1, membership.getPrice() );
			else
				throw new DTException( "MembershipManager.save: can't save a Membership: membership price is less than 0" );

			if( membership.getDate() != null )
				stmt.setDate( 2, (java.sql.Date)membership.getDate());
			else
				throw new DTException( "MembershipManager.save: can't save a Membership: firstName undefined" );


			if( membership.isPersistent() )
				stmt.setLong( 3, membership.getId() );

			inscnt = stmt.executeUpdate();

			if( !membership.isPersistent() ) {
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
							membershipId = r.getLong( 1 );
							if( membershipId > 0 )
								membership.setId( membershipId ); // set this person's db id (proxy object)
						}
					}
				}
			}
			else {
				if( inscnt < 1 )
					throw new DTException( "MembershipManager.save: failed to save a Membership" );
			}
		}
		catch( SQLException e ) {
			e.printStackTrace();
			throw new DTException( "MembershipManager.save: failed to save a Membership: " + e );
		}
		
	}

	/*
	public Iterator<Membership> restore(Membership membership) throws DTException{

		String       selectMembershipSql = "select price, date from membership";
		Statement    stmt = null;
		StringBuffer query = new StringBuffer( 100 );
		StringBuffer condition = new StringBuffer( 100 );

		condition.setLength( 0 );

		// form the query based on the given Membership object instance
		query.append( selectMembershipSql );

		if( membership != null ) {
			if( membership.getId() >= 0 ) // id is unique, so it is sufficient to get a registered membership
				query.append( " where id = " + membership.getId() );
			else {
				if( membership.getPrice() > 0 )
					condition.append( " price = '" + membership.getPrice() + "'" );

				if( membership.getDate() != null ) {
					if( condition.length() > 0 )
						condition.append( " and" );

					condition.append( " date = '" + (java.sql.Date)membership.getDate() + "'" );
				}

				if( condition.length() > 0 ) {
					query.append(  " where " );
					query.append( condition );
				}
			}
		}

		try {

			stmt = conn.createStatement();

			// retrieve the persistent Membership object
			//
			if( stmt.execute( query.toString() ) ) { // statement returned a result
				ResultSet r = stmt.getResultSet();
				return new MembershipIterator( r, objectModel );
			}
		}
		catch( Exception e ) {      // just in case...
			throw new DTException( "MembershipManager.restore: Could not restore persistent Membership object; Root cause: " + e );
		}

		// if we get to this point, it's an error
		throw new DTException( "Membership.restore: Could not restore persistent Membership object" );

	}*/
	
	public void delete(Membership membership) throws DTException{

		String               deleteMembershipSql = "delete from membership where id = ?";
		PreparedStatement    stmt = null;
		int                  inscnt;

		// form the query based on the given membership object instance
		if( !membership.isPersistent() ) // is the membership object persistent?  If not, nothing to actually delete
			return;

		try {

			//DELETE t1, t2 FROM t1, t2 WHERE t1.id = t2.id;
			//DELETE FROM t1, t2 USING t1, t2 WHERE t1.id = t2.id;
			stmt = (PreparedStatement) conn.prepareStatement( deleteMembershipSql );

			stmt.setLong( 1, membership.getId() );

			inscnt = stmt.executeUpdate();

			if( inscnt == 0 ) {
				throw new DTException( "membershipManager.delete: failed to delete this membership" );
			}
		}
		catch( SQLException e ) {
			throw new DTException("Membership.delete: failed to delete this Membership: " + e.getMessage());
		}
		
	}


	public Membership restore() {

		return null;

	}
	

}
