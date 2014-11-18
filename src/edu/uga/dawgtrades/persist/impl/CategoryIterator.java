package edu.uga.dawgtrades.persist.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;

public class CategoryIterator implements Iterator<Category> {

    private ResultSet rs = null;
    private ObjectModel objectModel = null;
    private boolean more;

    public CategoryIterator(ResultSet rs, ObjectModel objectModel) throws DTException {

        this.rs = rs;
        this.objectModel=objectModel;
        try{

            more = rs.next();

        }catch(Exception e){
            throw new DTException("CategoryIterator"+e);

        }
    }

    @Override
    public boolean hasNext() {

        return more;
    }

    @Override
    public Category next() {
        long id = -1;
        String name = null;
        long parent_id = -1;
        if(more){
            try{
                id = rs.getLong(1);
                name = rs.getString(2);
                parent_id = rs.getLong(3);
                more = rs.next();
            }
            catch(Exception e){

                throw new NoSuchElementException("CategoryIterator:Error in retrieving Category"+e);
            }
            Category parentCategory = null;
            if(parent_id>0){
                parentCategory = objectModel.createCategory();
                parentCategory.setId(parent_id);
            }
            try {
                Category category = objectModel.createCategory(parentCategory, name);
                category.setId(id);
                return category;
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