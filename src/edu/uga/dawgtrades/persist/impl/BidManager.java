package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class BidManager {

	private ObjectModel objectModel = null;
	private Connection  conn = null;

	public BidManager(Connection conn, ObjectModel objectModel) {

		this.conn = conn;
		this.objectModel = objectModel;
	}

	public void save(Bid bid) throws DTException{
		String insertBidSql = "insert into bid (user_id, auction_id, date, amount) values (?, ?, ?, ?)";
		PreparedStatement stmt;
		int inscnt;
		long bidId;
		//throw exceptions here; not important now

		try{
			stmt = conn.prepareStatement(insertBidSql);


			if( bid.getRegisteredUser() != null && bid.getRegisteredUser().getId() >= 0) // clubsuser is unique, so it is sufficient to get a person
				stmt.setLong(1, bid.getRegisteredUser().getId());
			else
				throw new DTException( "BidManager.save: can't save a Bid: registered user is undefined" );

			if( bid.getAuction() != null && bid.getAuction().getId() >= 0 )
				stmt.setLong(2, bid.getAuction().getId());
			else
				throw new DTException( "BidManager.save: can't save a Bid: auction is undefined" );

			if( bid.getDate() != null ) {
                java.sql.Date date = new java.sql.Date(bid.getDate().getTime());
                stmt.setDate(3, date);
            }
			else
				throw new DTException( "BidManager.save: can't save a Bid: date undefined" );

			if( bid.getAmount() >= 0 )
				stmt.setFloat(4, bid.getAmount());
			else
				throw new DTException( "BidManager.save: can't save a Bid: amount less than 0" );


			/*stmt.setLong(1, bid.getAuction().getId());
			stmt.setLong(2, bid.getRegisteredUser().getId());
			stmt.setFloat(3, bid.getAmount());*/

			inscnt = stmt.executeUpdate();

			if(inscnt >= 1){
				String sql = "select last_insert_id()";
				if(stmt.execute(sql)){
					ResultSet r = stmt.getResultSet();
					while(r.next()){
						bidId = r.getLong(1);
						if(bidId > 0){
							bid.setId(bidId);
						}
					}
				}
			}else{
				throw new DTException("BidManager.save: failed to save a Bid");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DTException("BidManager.save: failed to save a Bid:" + e);
		}
	}

	public Iterator<Bid> restore(Bid bid) throws DTException{
		String selectBidSql = "select id,user_id, auction_id, date, amount from bid";
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);

		condition.setLength(0);
		query.append(selectBidSql);
		if(bid != null){
			if(bid.isPersistent()){
				query.append("where id = " + bid.getId());
			}else{
				if(bid.getAuction() != null){
					if(condition.length()>0)
					condition.append(" and auction_id = " + bid.getAuction().getId());
					else
						condition.append(" auction_id= "+bid.getAuction().getId());
				}
				if(bid.getRegisteredUser() != null && bid.getRegisteredUser().getId() >= 0){
                    if(condition.length()>0)
					    condition.append(" and user_id = " + bid.getRegisteredUser().getId());
                    else
                        condition.append(" user_id = " + bid.getRegisteredUser().getId());
				}
				if(bid.getDate() != null){
                    if(condition.length() >0)
					    condition.append(" and date = " + bid.getDate());
                    else
                        condition.append(" date = " + bid.getDate());
				}
				if(bid.getAmount() != 0){
                    if(condition.length()>0)
					    condition.append(" and amount = " + bid.getAmount());
                    else
                        condition.append(" amount = " + bid.getAmount());
				}
				if(condition.length() > 0){
					query.append(  " where " );
					query.append(condition);
				}
			}
		}
		try{
			stmt = conn.createStatement();
			if(stmt.execute(query.toString())){
				ResultSet r = stmt.getResultSet();
				return new BidIterator(r, objectModel);
			}
		}catch(Exception e){
			throw new DTException("BidManager.restore: Could not restore persistent Bid object; Root cause: " + e);
		}
		throw new DTException("BidManager.restore: Could not restore persistent Bid object");
	}

	public void delete(Bid bid) throws DTException{
		String deleteBidSql = "delete from bid where id = ?";
		PreparedStatement stmt = null;
		int inscnt;

		if(!bid.isPersistent()){
			return;
		}
		try{
			stmt = (PreparedStatement) conn.prepareStatement(deleteBidSql);
			stmt.setLong(1, bid.getId());
			inscnt = stmt.executeUpdate();

			if(inscnt == 1){
				return;
			}else{
				throw new DTException("BidManager.delete failed");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DTException("BidManager.delete failed " + e);
		}
	}
}