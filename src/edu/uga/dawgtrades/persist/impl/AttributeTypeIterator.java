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

    public AttributeTypeIterator(ResultSet r, ObjectModel objectModel) throws DTException {

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
                name = rs.getString(2);
                category_id = rs.getLong(3);

                more = rs.next();

            }
            catch(Exception e){

                throw new NoSuchElementException("AttributeTypeIterator:No next AttributeType object"+e);
            }

            Category category = objectModel.createCategory();
            category.setId(category_id);

            try {
                AttributeType attributeType = objectModel.createAttributeType(category, name);
                return attributeType;
            } catch (DTException e) {

                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void remove() {

        throw new UnsupportedOperationException();

    }

}
