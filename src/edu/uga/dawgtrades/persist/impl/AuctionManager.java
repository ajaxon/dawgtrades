package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.util.Iterator;
import java.sql.*;
import java.util.Iterator;


import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;


import edu.uga.dawgtrades.model.Auction;


public class AuctionManager {

    private ObjectModel objectModel = null;
    private Connection conn = null;

    public AuctionManager(Connection conn, ObjectModel objectModel) {

        this.conn = conn;
        this.objectModel = objectModel;
    }


    public void save(Auction auction) throws DTException {


        String insertAuctionSql = "insert into auction (  expiration, minPrice, item_id ) values ( ?, ?, ? )";
        String updateAuctionSql = "update auction set expiration = ?, minPrice = ?, item_id = ? where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long auctionId;

        if (auction.getItemId() == -1)
            throw new DTException("AuctionManager.save: Attempting to save an Auction without an item");

        try {

            if (!auction.isPersistent())
                stmt = (PreparedStatement) conn.prepareStatement(insertAuctionSql);
            else
                stmt = (PreparedStatement) conn.prepareStatement(updateAuctionSql);


            if (auction.getExpiration() != null) {
                java.util.Date jDate = auction.getExpiration();
                java.sql.Date sDate = new java.sql.Date(jDate.getTime());
                stmt.setDate(1, sDate);
            } else
                stmt.setNull(1, java.sql.Types.DATE);

            stmt.setFloat(2, auction.getMinPrice());
            stmt.setLong(3, auction.getItemId());


            if (auction.isPersistent())
                stmt.setLong(4, auction.getId());
            inscnt = stmt.executeUpdate();

            if (!auction.isPersistent()) {
                if (inscnt >= 1) {
                    String sql = "select last_insert_id()";
                    if (stmt.execute(sql)) {
                        ResultSet r = stmt.getResultSet();

                        while (r.next()) {
                            auctionId = r.getLong(1);
                            if (auctionId > 0)
                                auction.setId(auctionId);
                        }
                    }
                } else
                    throw new DTException("AuctionManager.save: failed to save an Auction");
            } else {
                if (inscnt < 1)
                    throw new DTException("AuctionManager.save: failed to save an Auction:");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DTException("AuctionManager.save: failed to save an Auction: " + e);
        }
    }


    public Iterator<Auction> restore(Auction auction) throws DTException {


        String selectAuctionSql = "select id, expiration, minPrice, item_id from auction";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        // form the query based on the given RegisteredUser object instance
        query.append(selectAuctionSql);

        if (auction != null) {
            if (auction.getId() >= 0) // id is unique, so it is sufficient to get an auction
                query.append(" where id = " + auction.getId());

            else {
                if (auction.getMinPrice() > 0)
                    condition.append(" minPrice = '" + auction.getMinPrice() + "'");

                if (auction.getItemId() > 0) {
                    if (condition.length() > 0)
                        condition.append(" and");
                    condition.append(" item_id = '" + auction.getItemId() + "'");
                }
                /*
                if (auction.getExpiration() != null) // userName is unique, so it is sufficient to get a registered user
                    if (condition.length() > 0)
                        condition.append(" and");
                condition.append(" expiration = '" + auction.getExpiration() + "'");

*/
                if (condition.length() > 0) {
                    query.append(" where ");
                    query.append(condition);
                }
            }
        }

        try {

            stmt = (PreparedStatement)conn.prepareStatement(selectAuctionSql);

            // retrieve the persistent RegisteredUser object
            //
            if (stmt.execute(query.toString())) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new AuctionIterator(r, objectModel);
            }
        } catch (Exception e) {      // just in case...
            throw new DTException("RegisteredUserManager.restore: Could not restore persistent RegisteredUser object; Root cause: " + e);
        }

        // if we get to this point, it's an error
        throw new DTException("RegisteredUser.restore: Could not restore persistent RegisteredUser object");

    }


    public void delete(Auction auction) throws DTException {


        String deleteAuctionSql = "delete from auction where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        // form the query based on the given registeredUser object instance
        if (!auction.isPersistent()) // is the registeredUser object persistent?  If not, nothing to actually delete
            return;

        try {


            stmt = (PreparedStatement) conn.prepareStatement(deleteAuctionSql);

            stmt.setLong(1, auction.getId());

            inscnt = stmt.executeUpdate();

            if (inscnt == 0) {
                throw new DTException("AuctionManager.delete: failed to delete this Auction");
            }
        } catch (SQLException e) {
            throw new DTException("Auction.delete: failed to delete this Auction: " + e.getMessage());
        }
    }


    public Item restoreItemBy(Auction auction) throws DTException{

        String restoreItemBySql = "select i.id, i.name, i.identifier, i.description, i.owner_id, i.category_id from item I, auction A where I.id = A.item_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        if(auction != null)
        {
            if(auction.getId() > 0)
                query.append(" and A.id =" + auction.getId() + "'");
        }
        try {
            stmt = (PreparedStatement)conn.prepareStatement(restoreItemBySql);

            if (stmt.execute(query.toString())) {
                ResultSet r = stmt.getResultSet();
                Iterator<Item> itemIter = new ItemIterator(r, objectModel);
                if (itemIter != null && itemIter.hasNext()) {
                    return itemIter.next();
                } else
                    return null;
            }
        } catch(Exception e) {
            throw new DTException("AuctionManager.restoreItemBy: Could not restore Item " + e);
        }
        throw new DTException("AuctionManager.restoreItemBy: Could not restore Item ");
    }
}
