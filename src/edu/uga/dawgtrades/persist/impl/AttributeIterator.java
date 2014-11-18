package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;

public class AttributeIterator implements Iterator<Attribute> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more = false;

    public AttributeIterator(ResultSet rs, ObjectModel objectModel) throws DTException{

        this.rs=rs;
        this.objectModel=objectModel;
        try{


        }catch(Exception e){
            throw new DTException("AttributeIterator"+ e);

        }
    }

    @Override
    public boolean hasNext() {

        return more;
    }

    @Override
    public Attribute next() {

        long id=-1;
        long attribute_type_Id=-1;
        long itemId=-1;
        String value = null;

        if(more){
            try{
                id = rs.getLong(1);
                value = rs.getString(2);
                attribute_type_Id = rs.getLong(3);
                itemId = rs.getLong(4);

                more = rs.next();
            }
            catch(Exception e){

                throw new NoSuchElementException("AttributeIterator:No next Attribute object"+e);
            }

            Attribute attribute = objectModel.createAttribute();
            attribute.setId(id);
            attribute.setItemId(itemId);
            attribute.setAttributeType(attribute_type_Id);
            attribute.setValue(value);
            AttributeType attributeType = null;
            try {
                attributeType = objectModel.getAttributeType(attribute);
            } catch (DTException e2) {

                e2.printStackTrace();
            }
            Item item = null;
            try {
                item = objectModel.getItem(attribute);

            } catch (DTException e1) {

                e1.printStackTrace();
            }
            try{
                attribute = objectModel.createAttribute(attributeType, item, value);

                return attribute;
            }catch(DTException e){

                e.printStackTrace();
            }
        }else {

            throw new NoSuchElementException( "AttributeIterator: No next Attribute object" );

        }
        return null;

    }

    @Override
    public void remove() {


        throw new UnsupportedOperationException();
    }

}
