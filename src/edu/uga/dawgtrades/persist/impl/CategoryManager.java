package edu.uga.dawgtrades.persist.impl;

import java.sql.*;
import java.util.Iterator;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import jdk.internal.org.objectweb.asm.Type;

public class CategoryManager {

    private ObjectModel objectModel = null;
    private Connection  conn = null;

    public CategoryManager(Connection conn, ObjectModel objectModel) {

        this.conn = conn;
        this.objectModel = objectModel;
    }

    // test passing
    public void save(Category category) throws DTException{
        String insertCategorySql = "insert category ( name, parent_id ) values ( ?, ? )";
        String updateCategorySql = "update category set name = ?, parent_id = ? ";
        PreparedStatement stmt = null;
        int inscnt;
        long categoryId;

        try{
            if(!category.isPersistent()){

                stmt = (PreparedStatement) conn.prepareStatement(insertCategorySql);

            }else{

                stmt = (PreparedStatement) conn.prepareStatement(updateCategorySql);

            }
            stmt.setString(1,category.getName());
            if(category.getParentId() == 0 )
                stmt.setNull(2, Type.FLOAT);
            else
                stmt.setFloat(2, category.getParentId());

            if(category.isPersistent()){
                stmt.setFloat(3,category.getId());
            }

            inscnt = stmt.executeUpdate();

            if(!category.isPersistent()){
                if(inscnt >=1){
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql)){
                        ResultSet r = stmt.getResultSet();

                        while(r.next()){
                            categoryId = r.getLong(1);
                            if(categoryId>0){
                                category.setId(categoryId);
                            }
                        }
                    }


                }else{
                    throw new DTException("CategoryManager.save:Could not save object");

                }
            }else{
                if(inscnt <1){
                    throw new DTException("CategoryManager.save:Could not save category object");

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new DTException("CategoryManager.save:failed to save Category");

        }

    }
    // Test passing
    public Iterator<Category> restore(Category category) throws DTException{

        String selectCategorySql = "select id, name, parent_id from category";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);
        query.append(selectCategorySql);
        if(category!=null) {
            if (category.getId() >= 0) {
                query.append(" where id = '" + category.getId() + "'");
            } else {
                if (category.getParentId() > 0) {
                    condition.append(" parent_id = '" + category.getParentId() + "'");
                }
                if (category.getName() != null) {
                    if (condition.length() > 0) {
                        condition.append(" and");
                    }
                    condition.append(" name = '" + category.getName() + "'");

                }

                if (condition.length() > 0) {
                    query.append(" where ");
                    query.append(condition);
                }

            }
        }
            try{
                stmt = conn.createStatement();
                if(stmt.execute(query.toString())){
                    ResultSet r  = stmt.getResultSet();
                    return new CategoryIterator(r,objectModel);
                }
            }catch(Exception e){
                throw new DTException("CategoryManager.restore: could not restore categories");

            }

            throw new DTException("CategoryManager.restore: could not restore categories");





    }
    // Test passing
    public void delete(Category category) throws DTException{

        String deleteAttributeTypeSql = "delete from category where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if(!category.isPersistent()){

            throw new DTException("CategoryManager.delete:Category is not persistent");
        }
        try{
            stmt = (PreparedStatement) conn.prepareStatement(deleteAttributeTypeSql);
            stmt.setLong(1, category.getId());
            inscnt = stmt.executeUpdate();

            if(inscnt ==0){
                throw new DTException("CategoryManager.delete: Could not delete categorhy object");
            }
        }catch(SQLException e){
            throw new DTException("CategoryManager.delete:Could not delete category object");
        }
    }


    public Category restoreParentBy(Category category) throws DTException {

        String restoreParentBySql = "select P.id, P.name, P.parent_id from category P, category C where P.id = C.parent_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        if(category!=null){

            if(category.getId()>0){
                query.append(" and C.id='"+ category.getId()+"'");
            }
        }

        try{
            stmt = (PreparedStatement) conn.prepareStatement(restoreParentBySql);
            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                Iterator<Category> catIter = new CategoryIterator(r,objectModel);
                if(catIter!=null && catIter.hasNext()){
                    return catIter.next();
                }else
                    return null;
            }
        }catch(Exception e){

            throw new DTException("CategoryManager.restoreParentCategoryBy. Could not restore category"+e);
        }

        return null;
    }


    public Iterator<Category> restoreChildBy(Category category) throws DTException {
        String restoreChildBySql = "select C.id, C.name, C.parent_id from category C, category P where P.id = C.parent_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);

        if(category!=null){

            if(category.getId()>0){
                query.append("  and P.id='"+category.getId()+"'");
            }
        }

        try{

            stmt = (PreparedStatement) conn.prepareStatement(restoreChildBySql);
            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                Iterator<Category> catIter = new CategoryIterator(r,objectModel);
                if(catIter!=null){
                    return catIter;
                }
            }

        }catch(SQLException e){

            throw new DTException("CategoryManager.restoreChildBy. Could not restore children categories"+e);
        }


        return null;
    }

    // Test passed
    public Iterator<AttributeType> restoreAttributeTypeBy(Category category) throws DTException {
        String restoreAttributeBySql = "select A.id, A.category_id, A.name from attribute_type A, category C where A.category_id = C.id";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);
        condition.setLength(0);

        query.append(restoreAttributeBySql);

        if(category!=null){
            if(category.getId()>0){
                condition.append(" and C.id='"+category.getId()+"'");
            }
        }

        query.append(condition);
        try{

            stmt = conn.createStatement();
            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                return new AttributeTypeIterator(r,objectModel);
            }

        }catch(Exception e){
            throw new DTException("CategoryManager.restoreAttributeTypeBy:Could not restore attribute type objects");
        }
        throw new DTException("CategoryManager.restoreAttributeTypeBy:Could not restore attribute type objects");

    }

    // Test Passed
    public Iterator<Item> restoreItemBy(Category category) throws DTException {
        String restoreItemBySql = "select I.id, I.name, I.identifier, I.description, I.owner_id , I.category_id from item I, category C where C.id = I.category_id";
        Statement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);
        query.append(restoreItemBySql);

        if(category!=null){

            if(category.getId()>=0){
                condition.append(" and C.id ='"+category.getId()+"'");
            }

        }
        try{
            stmt = conn.createStatement();

            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                return new ItemIterator(r,objectModel);
            }
        }catch(Exception e){
            throw new DTException("CategoryManager.restoreItemBy: Could not restore items"+e);
        }

        throw new DTException("CategoryManager.restoreItemBy: Could not restore items");


    }


}