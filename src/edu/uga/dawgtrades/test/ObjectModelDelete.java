package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import edu.uga.dawgtrades.servlets.Register;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


public class ObjectModelDelete extends TestCase
{
    Connection  conn = null;
    ObjectModel objectModel = null;
    Persistence persistence = null;
    @BeforeClass
    public void setUp() throws DTException {


        // get a database connection
        try {
            conn = DbUtils.connect();
        }
        catch (Exception seq) {
            System.err.println( "ObjectModelRead: Unable to obtain a database connection" + seq.toString());
        }

        // obtain a reference to the ObjectModel module
        objectModel = new ObjectModelImpl();

        // obtain a reference to Persistence module and connect it to the ObjectModel
        persistence = new PersistenceImpl( conn, objectModel );

        // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence( persistence );



        //

    }
    @Test
    public static void testDeleteUser(ObjectModel objectModel, Persistence persistence) throws DTException{

        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);
        RegisteredUser userFind = objectModel.createRegisteredUser();
        userFind.setFirstName("Matt");
        Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser(userFind);
        int userCount = 0;
        RegisteredUser u = null;
        while(userIter.hasNext()){
            u = userIter.next();
            System.out.println(u.getName());
            userCount++;

        }
        assertEquals(userCount,1);
        System.out.println("User found");
        persistence.deleteRegisteredUser(u);
        userIter = objectModel.findRegisteredUser(userFind);
        userCount = 0;
        while(userIter.hasNext()){
            u = userIter.next();
            System.out.println(u.getName());
            userCount++;

        }
        assertEquals(userCount,0);
        System.out.println("User has been deleted");

    }

    @Test
    public static void testDeleteCategory(ObjectModel objectModel, Persistence persistence) throws DTException{
        Category category = objectModel.createCategory(null, "Games");
        Category category2 = null;
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount = 0;
        while(categories.hasNext()){
            category = categories.next();
            categoryCount++;
        }
        assertEquals(categoryCount,1);
        System.out.println("Category has been added");
        category2 = objectModel.createCategory(category, "Board Games");
        persistence.saveCategory(category2);
        categoryCount = 0;
        while(categories.hasNext()){
            category = categories.next();
            categoryCount++;
        }

        assertEquals(2,categoryCount);
        System.out.println("Category2 has been added");
        categories = objectModel.findCategory(null);
        categoryCount =0;
        while(categories.hasNext()){
            objectModel.deleteCategory(categories.next());
            categoryCount ++;
        }
        assertEquals(categoryCount,2);
        System.out.println("Two categories found");
        categories = objectModel.findCategory(null);
        categoryCount =0;
        while(categories.hasNext()){
            categoryCount ++;

        }
        assertEquals(categoryCount,0);
        System.out.println("Categories Deleted");


    }

    @Test
    public void test_DeleteExperienceReport() throws DTException{



        ExperienceReport report = null;
        Iterator<ExperienceReport> reports = objectModel.findExperienceReport(null);
        int experienceReportCount = 0;

        while(reports.hasNext()){
            experienceReportCount ++;
            report = reports.next();
        }
        assertEquals(1,experienceReportCount);

        persistence.deleteExperienceReport(report);
        reports = objectModel.findExperienceReport(null);
        experienceReportCount = 0;
        while(reports.hasNext()){
            experienceReportCount ++;
            report = reports.next();
        }
        assertEquals(0,experienceReportCount);
        System.out.println("Experience report deleted");

    }

    @Test
    public void test_DeleteAttributeAndItem(ObjectModel objectModel, Persistence persistence) throws DTException{
        Category category = objectModel.createCategory(null, "Games");
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount =0;
        while(categories.hasNext()){
            categoryCount++;
            category = categories.next();

        }
        assertEquals(categoryCount,1);
        System.out.println("Category properly added.");
        AttributeType attributeType = objectModel.createAttributeType(category, "Year Made");
        persistence.saveAttributeType(attributeType);
        Iterator<AttributeType> attributeTypes =  objectModel.getAttributeType(category);
        int attributeTypeCount = 0;
        while(attributeTypes.hasNext()){
            attributeType = attributeTypes.next();
            attributeTypeCount++;


        }

        assertEquals(attributeTypeCount,1);
        System.out.println("AttributeType added.");

        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);
        System.out.println("User Saved");
        Item item = objectModel.createItem(category, user1, "Identifier", "1960 Knife", "Its old");
        persistence.saveItem(item);

        Iterator<Item> itemIter = objectModel.findItem(null);
        int itemCount =0;
        while(itemIter.hasNext()){
            itemCount ++;
        }
        assertEquals(itemCount,0);
        System.out.println("Item saved");


        Attribute attribute = objectModel.createAttribute(attributeType, item, "1960");
        persistence.saveAttribute(attribute);
        Iterator<Attribute> attrIter = objectModel.getAttribute(item);
        int attributeCount =0;
        while(attrIter.hasNext()){
            attributeCount++;
        }
        assertEquals(attributeCount,1);
        System.out.println("Attribute saved");
        persistence.deleteAttribute(attribute);
        attributeCount = 0;
        attrIter = objectModel.getAttribute(item);
        while(attrIter.hasNext()){
            attributeCount++;
        }
        assertEquals(attributeCount,0);
        System.out.println("Attribute Deleted");

        persistence.deleteItem(item);
        itemIter = objectModel.findItem(null);
        itemCount = 0;
        while(itemIter.hasNext()){
            itemCount++;
        }
        assertEquals(itemCount,0);
        System.out.println("Item has been deleted");
    }

    @Test
    public void test_DeleteAuctionAndBid(ObjectModel objectModel, Persistence persistence) throws DTException{








        Auction auction = null;
        Iterator<Auction> auctions = objectModel.findAuction(null);
        int auctionCount =0;
        while(auctions.hasNext()){
            auction = auctions.next();
            auctionCount++;
        }
        assertEquals(1,auctionCount);



       Bid bid = null;
        Iterator<Bid> bids = objectModel.findBid(null);
        int bidCount = 0;
        while(bids.hasNext()){
            bid = bids.next();
            bidCount++;
        }
        assertEquals(1,bidCount);

        persistence.deleteBid(bid);
        bids = objectModel.findBid(null);
        bidCount = 0;
        while(bids.hasNext()){
            bidCount++;
        }
        assertEquals(0,bidCount);
        System.out.println("Bid deleted");

        persistence.deleteAuction(auction);
        auctions = objectModel.findAuction(null);
        auctionCount = 0;
        while(auctions.hasNext()){
            auctionCount++;
        }
        assertEquals(0,auctionCount);
        System.out.println("Auction deleted");

        Item item = null;
        int itemCount = 0;
        Iterator<Item> items = objectModel.findItem(null);
        while(items.hasNext()){
            item = items.next();
            itemCount++;
        }
        assertEquals("All items belonging to auction should have been deleted automatically",0,itemCount);

    }

    @Test
    public void test_DeleteAttributeType(ObjectModel objectModel, Persistence persistence) throws DTException{
        Category category = objectModel.createCategory(null, "Games");
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount =0;
        while(categories.hasNext()){
            categoryCount++;
            category = categories.next();

        }
        assertEquals(categoryCount,1);
        System.out.println("Category properly added.");
        AttributeType attributeType = objectModel.createAttributeType(category, "Year Made");
        persistence.saveAttributeType(attributeType);
        Iterator<AttributeType> attributeTypes =  objectModel.getAttributeType(category);
        int attributeTypeCount = 0;
        while(attributeTypes.hasNext()){
            attributeType = attributeTypes.next();
            attributeTypeCount++;


        }

        assertEquals(attributeTypeCount,1);
        System.out.println("AttributeType added.");
        attributeTypes = objectModel.getAttributeType(category);
        attributeTypeCount=0;
        while(attributeTypes.hasNext()){
            objectModel.deleteAttributeType(attributeType);
            attributeTypeCount++;
        }

        assertEquals(attributeTypeCount,1);
        attributeTypeCount=0;
        attributeTypes = objectModel.getAttributeType(category);
        while(attributeTypes.hasNext()){
            attributeTypeCount++;
        }
        assertEquals(attributeTypeCount,0);
        System.out.println("AttributeType deleted.");


    }
    @Test
    public void test_DeleteMembership(ObjectModel objectModel, Persistence persistence) throws DTException{

        Membership membership = objectModel.findMembership();
        assertTrue(membership.getId()>0);


        persistence.deleteMembership(membership);
        Membership membership2 = objectModel.findMembership();
        assertTrue(!membership2.isPersistent());
        System.out.println("Membership has been deleted");

    }









}