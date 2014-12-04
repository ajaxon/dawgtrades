package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        String insertMembershipSql = "insert into membership(price,date) values(?,?)";
        String updateMembershipSql = "update membership price = ?, date = ? where id = ?";
        PreparedStatement stmt;
        int inscnt;
        long membershipId;
        try{
            if(!membership.isPersistent()){

                stmt = (PreparedStatement) conn.prepareStatement(insertMembershipSql);
            }else{
                stmt = (PreparedStatement) conn.prepareStatement(updateMembershipSql);
            }

            stmt.setFloat(1,membership.getPrice());
            java.sql.Date date = new java.sql.Date(membership.getDate().getTime());
            stmt.setDate(2,date);

            if(membership.isPersistent()){
                stmt.setLong(3, membership.getId());
            }

            inscnt = stmt.executeUpdate();

            if(!membership.isPersistent()){
                if(inscnt >=1){

                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet r = stmt.getResultSet();

                        while(r.next()){
                            membershipId = r.getLong(1);
                            if(membershipId>0){

                                membership.setId(membershipId);

                            }


                        }
                    }
                }else{
                    throw new DTException("AttributeTypeManager.save: failed to save AttributeType");
                }


            }else{
                if(inscnt <1){
                    throw new DTException("MembershipManager.save: failed to save Membership object");
                }

            }


        }catch(SQLException e){
            e.printStackTrace();
            throw new DTException("MembershipManager.save:failed to save");

        }
    }

    public Iterator<Membership> restore(Membership membership) throws DTException{
        String selectMembershipSql = "select id, price, date from membership";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        if(membership!=null){
            if(membership.getId()>=0){
                query.append(" where id = '"+membership.getId()+"'");

            }
            try{
                stmt = (PreparedStatement) conn.prepareStatement(selectMembershipSql);
                if(stmt.equals(query.toString())){
                    ResultSet r = stmt.getResultSet();
                    return new MembershipIterator(r,objectModel);
                }

            }catch(Exception e){
                throw new DTException("MembershipManager.restore:Could not restore membership objects");
            }



        }

        return null;


    }

    public void delete(Membership membership) throws DTException{
        String deleteAttributeTypeSql = "delete from membership where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        if(!membership.isPersistent()){

            throw new DTException("MembershipManager.delete:could not delete");

        }

        try{
            stmt = (PreparedStatement) conn.prepareStatement(deleteAttributeTypeSql);
            stmt.setLong(1, membership.getId());
            inscnt = stmt.executeUpdate();
            if(inscnt ==0){
                throw new DTException("MembershipManager.delete:Could not delete. Error in query");
            }


        }catch(SQLException e){
            throw new DTException("MembershipManager.delete:Could nor delete membership object");
        }

    }


    public Membership restore() throws DTException {
        String selectMembershipSql = "select id, price, date from membership";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(selectMembershipSql);

        try{
            stmt = conn.createStatement();
            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                MembershipIterator memberIter = new MembershipIterator(r,objectModel);
                if(memberIter.hasNext()){
                    return memberIter.next();
                }else{
                    return null;
                }

            }
        }catch(Exception e){
            throw new DTException("MembershipManager.restore: " + e.toString());
        }

        return null;

    }


}