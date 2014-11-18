package edu.uga.dawgtrades.persist.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeManager {

    private ObjectModel objectModel = null;
    private Connection  conn = null;

    public AttributeManager(Connection conn, ObjectModel objectModel) {

        this.conn = conn;
        this.objectModel = objectModel;
    }


    public void save(Attribute attribute) throws DTException{

        String insertAttributeSql = "insert into attribute ( value,attribute_type_id,item_id ) values (?,?,?);";
        String updateAttributeSql = "update attribute set value= ?, attribute_type_id= ? item_id = ? where id = ?";
        PreparedStatement stmt = null;
        int inscnt;
        long attributeId;

        if(attribute.getItemId() == -1){
            throw new DTException("AttributeManager.save: Attempting to save an Attribute without an item");
        }
        try{

            if(!attribute.isPersistent()){
                stmt = (PreparedStatement) conn.prepareStatement(insertAttributeSql);
            }else{
                stmt = (PreparedStatement) conn.prepareStatement(updateAttributeSql);
            }

            stmt.setString(1, attribute.getValue());
            stmt.setLong(2,attribute.getAttributeType());
            stmt.setLong(3, attribute.getItemId());

            if(attribute.isPersistent()){
                stmt.setLong(4, attribute.getId());

            }

            inscnt = stmt.executeUpdate();
            if(!attribute.isPersistent()){

                if(inscnt >=1){
                    String sql = "select last_insert_id()";
                    if(stmt.execute(sql));
                    ResultSet r = stmt.getResultSet();

                    while(r.next()){
                        attributeId = r.getLong(1);
                        if(attributeId >0){
                            attribute.setId(attributeId);
                        }
                    }
                }else
                    throw new DTException("AttributeManager.save: failed to save an Attribute");
            }else {

                if(inscnt<1){
                    throw new DTException("AttributeManager.save: failed to save an Attribute");
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
            throw new DTException("AttributeManager.save: failed to save an Attribute"+ e);
        }


    }




    public Iterator<Attribute> restore(Attribute attribute) throws DTException{

        String selectAttributeSql = "select id, value, attribute_type_id, item_Id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        query.append(selectAttributeSql);

        if(attribute!=null){
            if(attribute.getId() >=0){
                query.append(" where id = "+attribute.getId());
            }
            else {
                if(attribute.getValue()!= null){

                    condition.append(" where value ='"+attribute.getValue()+"'");
                }
                if(attribute.getItemId() >=0){
                    if(condition.length() > 0)
                    {
                        condition.append(" and");
                    }
                    condition.append(" where item_id='"+attribute.getItemId()+"'");


                }

                if(attribute.getAttributeType() >=0){
                    if(condition.length() > 0)
                    {
                        condition.append(" and");
                    }
                    condition.append(" where attribute_type_id='"+attribute.getAttributeType()+"'");
                }

                if(condition.length()>0){
                    query.append(" where ");
                    query.append(condition);
                }
            }

        }

        try{

            stmt = (PreparedStatement)conn.prepareStatement(selectAttributeSql);


            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                return new AttributeIterator(r, objectModel);
            }

        }catch(Exception e){

            throw new DTException("AttributeManager.restore: Could not restore persistent AttributeManager");
        }



        throw new DTException("AttributeManager.restore: Could not restore persistent AttributeManager");

    }

    public void delete(Attribute attribute) throws DTException{

        String deleteAttributeSql = "delete from attribute where id = ?";
        PreparedStatement stmt = null;
        int inscnt;

        if(!attribute.isPersistent()){

            throw new DTException("AttributeManager.delete:Attribute is not a persistent entity");
        }

        try{

            stmt = (PreparedStatement) conn.prepareStatement(deleteAttributeSql);
            stmt.setLong(1, attribute.getId());

            inscnt = stmt.executeUpdate();

            if(inscnt ==0){

                throw new DTException("AttributeManager.delete: failed to delete Attribute");

            }


        }catch(SQLException e){

            throw new DTException("AttributeManager.delete:failed to delete this Attribute"+e.getMessage());
        }

    }


    public Item restoreItemBy(Attribute attribute) throws DTException {
        String restoreItemBySql = "select i.id, i.name, i.category_id, i.description, i.identifier, i.owner_id from item I, attribute A where I.id = A.item_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        if(attribute!=null){

            if(attribute.getId()> 0){

                query.append(" and A.id='"+attribute.getId()+"'");

            }
        }
        try{
            stmt = (PreparedStatement)conn.prepareStatement(restoreItemBySql);

            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                Iterator<Item> itemIter = new ItemIterator(r,objectModel);
                if(itemIter!=null && itemIter.hasNext()){

                    return itemIter.next();
                }else{

                    return null;
                }
            }

        }catch(Exception e){

            throw new DTException("AttributeManager.restoreItemBy: Could not restore Item"+e);
        }


        throw new DTException("AttributeManager.restoreItemBy: Could not restore Item");




    }



    public AttributeType restoreAttributeTypeBy(Attribute attribute) throws DTException {

        String restoreAttributeTypeBySql = "select t.id, t.name, t.category_id from attributeType T, attribute A where T.id = A.attribute_type_id";
        PreparedStatement stmt = null;
        StringBuffer query = new StringBuffer(100);
        StringBuffer condition = new StringBuffer(100);

        condition.setLength(0);

        if(attribute!=null){

            if(attribute.getId()> 0){

                query.append(" and A.id='"+attribute.getId()+"'");

            }
        }
        try{
            stmt = (PreparedStatement)conn.prepareStatement(restoreAttributeTypeBySql);

            if(stmt.execute(query.toString())){
                ResultSet r = stmt.getResultSet();
                Iterator<AttributeType> attributeTypeIter = new AttributeTypeIterator(r,objectModel);
                if(attributeTypeIter!=null && attributeTypeIter.hasNext()){

                    return attributeTypeIter.next();
                }else{

                    return null;
                }
            }

        }catch(Exception e){

            throw new DTException("AttributeManager.restoreAttributeTypeBy: Could not restore AttributeType"+e);
        }


        throw new DTException("AttributeManager.restoreAttributeTypeBy: Could not restore Item");




    }


}