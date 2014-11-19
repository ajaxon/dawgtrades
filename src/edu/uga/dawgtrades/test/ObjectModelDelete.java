package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

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
        assertEquals(categoryCount,2);
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
    public static void testDeleteExperienceReport(ObjectModel objectModel, Persistence persistence) throws DTException{

        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        RegisteredUser user2 =objectModel.createRegisteredUser("jam", "Steve", "Ya", "ok", false, "eml@uga.edu", "423432432", false);
        persistence.saveRegisteredUser(user1);
        persistence.saveRegisteredUser(user2);
        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(user1);
        while(users.hasNext()){
            user1 = users.next();
        }
        users = objectModel.findRegisteredUser(user2);
        while(users.hasNext()){
            user2 = users.next();
        }

        ExperienceReport report = objectModel.createExperienceReport(user1, user2, 4, "Cool time", new Date());
        persistence.saveExperienceReport(report);
        Iterator<ExperienceReport> reports = objectModel.findExperienceReport(null);
        int experienceReportCount = 0;

        while(reports.hasNext()){
            experienceReportCount ++;
            report = reports.next();
        }
        assertEquals(experienceReportCount,1);
        System.out.println("Experience report added");
        persistence.deleteExperienceReport(report);
        reports = objectModel.findExperienceReport(null);
        experienceReportCount = 0;
        while(reports.hasNext()){
            experienceReportCount ++;
            report = reports.next();
        }
        assertEquals(experienceReportCount,0);
        System.out.println("Experience report deleted");

    }

    @Test
    public static void testDeleteAttributeAndItem(ObjectModel objectModel, Persistence persistence) throws DTException{
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
            item = itemIter.next();
            itemCount ++;
        }
        assertEquals(itemCount,1);
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
    public static void testDeleteAuctionAndBid(ObjectModel objectModel, Persistence persistence) throws DTException{


        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);

        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(null);
        while(users.hasNext()){
            user1 = users.next();
        }

        Category category = objectModel.createCategory(null, "Game");
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        while(categories.hasNext()){
            category = categories.next();
        }
        Item item = objectModel.createItem(category, user1, "Monopoly", "Monopoly 20", "Cool Game");
        persistence.saveItem(item);

        Iterator<Item> items = objectModel.findItem(null);
        while(items.hasNext()){
            item = items.next();
        }
        Auction auction = objectModel.createAuction(item, 20, new Date());
        persistence.saveAuction(auction);
        Iterator<Auction> auctions = objectModel.findAuction(null);
        int auctionCount =0;
        while(auctions.hasNext()){
            auction = auctions.next();
            auctionCount++;
        }
        assertEquals(auctionCount,1);
        System.out.println("Auction Properly Created");

        RegisteredUser user2 =objectModel.createRegisteredUser("jam", "Steve", "Ya", "ok", false, "eml@uga.edu", "423432432", false);
        persistence.saveRegisteredUser(user2);
        users = objectModel.findRegisteredUser(user2);
        while(users.hasNext()){
            user2 = users.next();
        }
        assertTrue(user2.getId()>0);
        System.out.println("User 2 found");
        Bid bid = objectModel.createBid(auction, user2, 40);
        persistence.saveBid(bid);
        Iterator<Bid> bids = objectModel.findBid(null);
        int bidCount = 0;
        while(bids.hasNext()){
            bid = bids.next();
            bidCount++;
        }
        assertEquals(bidCount,1);
        System.out.println("Bid properly added");
        persistence.deleteBid(bid);
        bids = objectModel.findBid(null);
        bidCount = 0;
        while(bids.hasNext()){
            bidCount++;
        }
        assertEquals(bidCount,0);
        System.out.println("Bid deleted");

        persistence.deleteAuction(auction);
        auctions = objectModel.findAuction(null);
        auctionCount = 0;
        while(auctions.hasNext()){
            auctionCount++;
        }
        assertEquals(auctionCount,0);
        System.out.println("Auction deleted");


    }

    @Test
    public static void testDeleteAttributeType(ObjectModel objectModel, Persistence persistence) throws DTException{
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

    public static void testDeleteMembership(ObjectModel objectModel, Persistence persistence) throws DTException{
        Membership membership = objectModel.createMembership(10, new Date());
        persistence.saveMembership(membership);
        membership = objectModel.findMembership();
        assertTrue(membership.getId()>0);
        System.out.println("Membership saved.");
        persistence.deleteMembership(membership);
        Membership membership2 = objectModel.findMembership();
        assertTrue(!membership2.isPersistent());
        System.out.println("Membership has been deleted");

    }



    public static void deleteAllInfoFromTables(Connection conn) throws SQLException{
        PreparedStatement stmt = (PreparedStatement) conn.createStatement();
        String sql = "DELETE FROM user";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM attribute";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM attribute_type";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM auction";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM bid";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM category";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM experience_report";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM item";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM membership";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM user";
        stmt.executeUpdate(sql);



    }


    public static void main(String[] args)
    {
        Connection  conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;

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


        try {

            deleteAllInfoFromTables(conn);
            //Delete User Test
            testDeleteUser(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            //Delete Category Test
            testDeleteCategory(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            //Delete AttributeType Test
            testDeleteAttributeType(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            //Delete Attribute & Item Test
            testDeleteAttributeAndItem(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testDeleteExperienceReport(objectModel,persistence);
            deleteAllInfoFromTables(conn);

        }
        catch( DTException ce )
        {
            System.err.println( "DTException: " + ce );
            ce.printStackTrace();
        }
        catch( Exception e )
        {
            System.err.println( "Exception: " + e );
            e.printStackTrace();
        }
        finally {
            // close the connection
            try {
                conn.close();
            }
            catch( Exception e ) {
                System.err.println( "Exception: " + e );
            }
        }
    }


}