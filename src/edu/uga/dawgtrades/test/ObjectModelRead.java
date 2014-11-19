package edu.uga.dawgtrades.test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Allen on 11/16/14.
 */
public class ObjectModelRead extends TestCase
{
    static Connection  conn = null;
    static ObjectModel objectModel = null;
    static Persistence persistence = null;
    Category category =  null;
    Category child = null;
    RegisteredUser user = null;
    Item item  = null;
    Auction auction = null;
    Attribute attribute = null;
    Bid bid = null;
    //AttributeType attributeType = null;
    @Before
    public void setUp() throws DTException {


        // get a database connection
        try {
            conn = DbUtils.connect();
        }
        catch (Exception seq) {
            System.err.println( "ObjectModelDelete: Unable to obtain a database connection" );
        }

        // obtain a reference to the ObjectModel module
        objectModel = new ObjectModelImpl();

        // obtain a reference to Persistence module and connect it to the ObjectModel
        persistence = new PersistenceImpl( conn, objectModel );

        // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence( persistence );


        user = getUser();
        category = getCategory();
        child = getChild();
        item = getItem();
        attribute = getAttribute();
        auction = getAuction();
        bid = getBid();
        //attributeType = getAttributeType();

        //

    }
    /*
    public AttributeType getAttributeType() throws DTException {
        AttributeType attribute_type = null;
        Iterator<AttributeType> attrs = objectModel.getAttributeType(category);
        while(attrs.hasNext()){
            attribute_type = attrs.next();
            if(attribute_type.getName()=="Brand")
                break;

        }
        return attributeType;

    }
    */
    public Attribute getAttribute() throws DTException {
        Attribute attribute =  null;
        Iterator<Attribute> attrs = objectModel.getAttribute(item);
        while(attrs.hasNext()){
            attribute = attrs.next();
            if(attribute.getValue()=="Apple")
                break;
        }
        return attribute;
    }
    public RegisteredUser getUser() throws DTException {
        // Get the test user
        RegisteredUser modelUser = objectModel.createRegisteredUser();
        modelUser.setName("Test_name");
        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(modelUser);
        while(users.hasNext()){
            user = users.next();
        }
        return user;
    }
    public Category getCategory() throws DTException {
        // Get a category
        Category model = objectModel.createCategory();
        model.setName("Computers");
        Iterator<Category> categories = objectModel.findCategory(model);
        while(categories.hasNext()){
            category = categories.next();

        }
        return category;
    }
    public Category getChild() throws DTException {
        Iterator<Category> children = null;
        Category category = null;
        Category child = null;
        Category model = objectModel.createCategory();
        model.setName("Computers");
        Iterator<Category> categories = objectModel.findCategory(model);
        while(categories.hasNext()){
            category = categories.next();

        }

        children = objectModel.getChild(category);
        while(children.hasNext())
            child = children.next();
        return child;
    }
    public Item getItem() throws DTException {
        Item item = null;
        Item model = objectModel.createItem();
        model.setIdentifier("Test");
        Iterator<Item> items = objectModel.findItem(model);
        while(items.hasNext()){
            item = items.next();
        }
        return item;
    }
    public Bid getBid() throws DTException {

        Bid bid = null;
        Bid model = objectModel.createBid();
        model.setAmount(2.0f);
        Iterator<Bid> bids = objectModel.findBid(model);
        while(bids.hasNext()){
            bid = bids.next();
        }
        return bid;
    }

    public Auction getAuction() throws DTException {
        Auction auction = null;
        Auction model = objectModel.createAuction();
        model.setMinPrice(5.0f);
        Iterator<Auction> auctions = objectModel.findAuction(model);
        while(auctions.hasNext())
        {
            auction = auctions.next();


        }
        return auction;
    }

    // RegisteredUser
    @Test
    public void testgetAllUsers() throws DTException {
        System.out.println( "User objects:" );
        Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser( null );
        int userCount = 0;
        while( userIter.hasNext() ) {
            RegisteredUser u = userIter.next();
            System.out.println( u );
            userCount++;

        }
        assertEquals(userCount,4);

    }
    @Test
    public void test_Username()
    {
        assertEquals("Test_name",user.getName());
    }
    @Test
    public void test_restoreItemsByUser() throws DTException {

        int itemsCount = 0;


        Iterator<Item> items = objectModel.getItem(user);
        while(items.hasNext()){
            Item item = items.next();
            itemsCount++;
        }
        assertEquals(1,itemsCount);

    }
    // Category
    @Test
    public void test_restoreParentByCategory() throws DTException {
        Category parent = objectModel.getParent(child);
        assertEquals("Computers",parent.getName());

    }
    @Test
    public void test_getCategoryName()
    {
        assertEquals("Computers",category.getName());
    }

    @Test
    public void testgetAllCategories() throws DTException {
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount = 0;
        while(categories.hasNext()){
            Category cat = categories.next();
            categoryCount++;
        }
        assertEquals(2,categoryCount);
    }
    @Test // Restore item by category
    public void test_restoreItemsByCategory() throws DTException {
        Iterator<Item> items = objectModel.getItem(category);
        int itemcount = 0;
        while(items.hasNext()){
            Item item = items.next();
            itemcount++;
        }
        assertEquals(1,itemcount);
    }
    @Test // Restore attributesType by category
    public void test_restoreAttributeTypesbyCategory() throws DTException {
       Iterator< AttributeType> attrs = objectModel.getAttributeType(category);
       int attrcount = 0;
        while(attrs.hasNext()){
            AttributeType attr = attrs.next();
            attrcount++;

        }
        assertEquals(2,attrcount);

    }
    @Test // restore child by category CategoryManager.java
    public void test_restoreChildByCategory() throws DTException {
        Iterator<Category> child = objectModel.getChild(category);
        int childcount = 0;
        Category c = null;
        while(child.hasNext())
        {
            c = child.next();
            childcount++;
        }
        assertEquals(1,childcount);
        assertEquals("Laptops",c.getName());

    }

    ////// BIDS
    @Test
    public void test_getAllBids() throws DTException {

        Iterator<Bid> bidIter = objectModel.findBid(null);
        int bidCount = 0;
        while( bidIter.hasNext() ) {
            Bid u = bidIter.next();

            bidCount++;

        }
        assertEquals(1,bidCount);
    }
    @Test
    public void test_bidAmount(){
        assertEquals(2.0f,bid.getAmount());
    }

    // Attributes
    @Test
    public void test_getAttributes() throws DTException {
        Attribute attribute = null;
        Iterator<Attribute> attributes = objectModel.getAttribute(item);
        int attrcount =0;
        while(attributes.hasNext()){
            attribute = attributes.next();
            attrcount++;
        }
        assertEquals(1,attrcount);

    }

    @Test

    public void test_restoreItemByAttribute() throws DTException {
        Item item = objectModel.getItem(attribute);
        assertEquals("Macbook Air",item.getName());

    }

    @Test
    public void test_restoreAttributeTypebyAttribute() throws DTException {
        AttributeType attrType = objectModel.getAttributeType(attribute);
        assertEquals("Manufacturer",attrType.getName());
    }
    // AttributeType
    /*
    @Test
    public void test_restoreCategoryByAttributeType() throws DTException {

        Category category = objectModel.getCategory(attributeType);

    }
    */
    @Test
    public void test_restoreItemByAuction() throws DTException {
        Item item = objectModel.getItem(auction);
        assertEquals("Macbook Air",item.getName());
        assertEquals("Test",item.getIdentifier());

    }

    // Experience Report

    // Items
    @Test
    public void testcheckAllItems() throws DTException {

        Iterator<Item> items = objectModel.findItem(null);
        int itemcount =0;
        while(items.hasNext()){
            Item item = items.next();
            itemcount++;
        }
        assertEquals(1,itemcount);


    }
    @Test
    public void test_restoreCategoryByItem() throws DTException {
        Category category = objectModel.getCategory(item);
        assertEquals("Computers",category.getName());


    }
    public void test_restoreAttributeByItem() throws DTException {
        Attribute attr  = null;
        int attrcount =0;
        Iterator<Attribute> attributes = objectModel.getAttribute(item);
        while(attributes.hasNext())
        {
            attr = attributes.next();
            attrcount++;
        }
        assertEquals(1, attrcount);
    }
    public void test_restoreRegisteredUserbyItem() throws DTException {
        RegisteredUser user = objectModel.getRegisteredUser(item);
        assertEquals("Test_name",user.getName());

    }
    public void test_restoreAuctionbyItem() throws DTException {
        Auction auction = objectModel.getAuction(item);
        assertEquals(5.0f,auction.getMinPrice());
    }
    // Membership



    //


    @AfterClass
    public void teardown() throws SQLException {
        conn.close();

}
}