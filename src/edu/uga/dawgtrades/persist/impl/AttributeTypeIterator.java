package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeTypeIterator implements Iterator<AttributeType> {


    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more;

    public AttributeTypeIterator(ResultSet rs, ObjectModel objectModel) throws DTException {

        this.rs=rs;
        this.objectModel=objectModel;
        try{

            more = rs.next();

        }catch(Exception e){
            throw new DTException("AttributeIterator"+ e);

        }

    }

    @Override
    public boolean hasNext() {



        return more;
    }

    @Override
    public AttributeType next() {

        long id = -1;
        String name = null;
        long category_id = -1;

        if(more){

            try{
                id = rs.getLong(1);
                category_id= rs.getLong(2);
                name = rs.getString(3);

                more = rs.next();

            }
            catch(Exception e){

                throw new NoSuchElementException("AttributeTypeIterator:No next AttributeType object"+e);
            }

            AttributeType attributeType = null;

            attributeType = objectModel.createAttributeType();
            attributeType.setCategoryId(category_id);
            attributeType.setName(name);
            attributeType.setId(id);
            return attributeType;
        }else{
            throw new NoSuchElementException("AttributeTypeIterator:No next AttributeType object");
        }


    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();

    }

}