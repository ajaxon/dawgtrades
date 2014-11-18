package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
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
		String insertBidSql = "insert into bid (auction, user, price) values (?, ?, ?)";
		PreparedStatement stmt;
		int inscnt;
		long bidId;
		//throw exceptions here; not important now

		try{
			stmt = (PreparedStatement) conn.prepareStatement(insertBidSql);
			stmt.setLong(1, bid.getAuction().getId());
			stmt.setLong(2, bid.getRegisteredUser().getId());
			stmt.setFloat(3, bid.getAmount());
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
				throw new DTException("BidManager.save: failed to save a Membership");
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DTException("BidManager.save: failed to save a Membership:" + e);
		}
	}

	public Iterator<Bid> restore(Bid bid) throws DTException{
		String selectBidSql = "select and some stuff I'll implement later"; //implement later
		Statement stmt = null;
		StringBuffer query = new StringBuffer(100);
		StringBuffer condition = new StringBuffer(100);

		//throw errors here all over

		condition.setLength(0);
		query.append(selectBidSql);
		if(bid != null){
			if(bid.isPersistent()){
				query.append("where id = " + bid.getId());
			}else{
				if(bid.getAuction() != null){
					condition.append(" and auctionid = " + bid.getAuction().getId());
				}
				if(bid.getRegisteredUser() != null){
					condition.append(" and regUserid = " + bid.getRegisteredUser().getId());
				}
				if(bid.getAmount() != 0){
					condition.append(" and amount = " + bid.getAmount());
				}
				if(condition.length() > 0){
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