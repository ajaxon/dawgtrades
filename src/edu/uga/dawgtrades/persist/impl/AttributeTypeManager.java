package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeTypeManager {

    private ObjectModel objectModel = null;
    private Connection  conn = null;
    public AttributeTypeManager(Connection conn, ObjectModel objectModel) {

        this.conn = conn;
        this.objectModel = objectModel;
    }



    public void save(AttributeType attributeType) throws DTException{

        String insertAttributeTypeSql = "insert into attribute_type (category_id ,name) values (?,?)";
        String updateAttributeTypeSql = "update attribute_type set category_id=?, name = ? where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long attributeTypeId;

        if(attributeType.getCategoryId() ==-1){

            throw new DTException("AttributeTypeManager.save: Attempting to save an AttributeType without a category");

        }


        try{
            if(!attributeType.isPersistent()){

                stmt = (PreparedStatement) conn.prepareStatement(insertAttributeTypeSql);

            }else{

                stmt = (PreparedStatement) conn.prepareStatement(updateAttributeTypeSql);
            }

            stmt.setFloat(1, attributeType.getCategoryId());
            stmt.setString(2, attributeType.getName());

            if(attributeType.isPersistent()){
                stmt.setFloat(3,attributeType.getId());
            }
            inscnt = stmt.executeUpdate();

            if(!attributeType.isPersistent()){
                if(inscnt >=1){
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet r = stmt.getResultSet();

                        while(r.next()){
                            attributeTypeId = r.getLong(1);
                            if(attributeTypeId>0){

                                attributeType.setId(attributeTypeId);

                            }


                        }
                    }
                }else{
                    throw new DTException("AttributeTypeManager.save: failed to save AttributeType");
                }
            }else{
                if(inscnt <1){
                    throw new DTException("AttributeTypeManager.save: failed to save AttributeType");
                }

            }

        }catch(SQLException e){

            e.printStackTrace();
            throw new DTException("AttributeTypeManager.save: failed to save AttributeType"+e);

        }

    }

    public Iterator<AttributeType> restore(AttributeType attributeType) throws DTException {

        String       selectRegisteredUserSql = "select id, name, category_id from attribute_type";
        Statement stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        // form the query based on the given RegisteredUser object instance
        query.append( selectRegisteredUserSql );

        if( attributeType != null ) {
            if( attributeType.getId() >= 0 ) // id is unique, so it is sufficient
                query.append( " where id = " + attributeType.getId() );

            else {
                if( attributeType.getName() != null )
                    condition.append( " name = '" + attributeType.getId() + "'" );

                if( attributeType.getCategoryId() >= 0 ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " category_id = '" + attributeType.getCategoryId() + "'" );
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
                return new AttributeTypeIterator( r, objectModel );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new DTException( "AttTypeManager.restore: Could not restore persistent Attribute Type object; Root cause: " + e );
        }

        // if we get to this point, it's an error
        throw new DTException( "AttTypeManager.restore: Could not restore persistent Attribute Type Manager object" );
    }

    public void delete(AttributeType attributeType) throws DTException{

        String deleteAttributeTypeSql = "delete from attribute_type where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if(!attributeType.isPersistent()){

            throw new DTException("AttributeTypeManager.delete:Could not delete AttributeType object because the object was not persistent");
        }

        try{
            stmt = (PreparedStatement) conn.prepareStatement(deleteAttributeTypeSql);
            stmt.setLong(1,  attributeType.getId());
            inscnt = stmt.executeUpdate();

            if(inscnt ==0){

                throw new DTException("AttributeTypeManager.delete:Could not delete AttributeType object because the object was not persistent");
            }

        }catch(SQLException e){
            throw new DTException("AttributeTypeManager.delete:Could not delete AttributeType object");
        }

    }






    public Category restoreCategoryBy(AttributeType attributeType) throws DTException {

        String restoreCategoryBySql = "select c.id, c.name, c.parent_id from category C, attribute_type A where C.id = A.category_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        if(attributeType != null){

            if(attributeType.getId()>0){
                query.append(" and A.id ='" + attributeType.getId()+"'");
            }

        }

        try{
            stmt = (PreparedStatement) conn.prepareStatement(restoreCategoryBySql);

            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                Iterator<Category> catIter = new CategoryIterator(r,objectModel);
                if(catIter!=null && catIter.hasNext()){
                    return catIter.next();
                }else
                    return null;
            }
        }catch(Exception e){

            throw new DTException("AttributeTypeManager.restoreCategoryBy. Could not restore category"+e);
        }
        return null;
    }

}