package edu.uga.dawgtrades.test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Auction;
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

public class ObjectModelUpdate
{
    public static void deleteAllInfoFromTables(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();
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
            System.err.println( "ObjectModelUpdate: Unable to obtain a database connection" );
        }

        // obtain a reference to the ObjectModel module
        objectModel = new ObjectModelImpl();
        // obtain a reference to Persistence module and connect it to the ObjectModel
        persistence = new PersistenceImpl( conn, objectModel );
        // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence( persistence );


        try {

            deleteAllInfoFromTables(conn);
            testUserUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testMembershipUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testExperienceReport(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testCategoryUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testAttributeTypeUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testItemUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testAttributeUpdate(objectModel,persistence);
            deleteAllInfoFromTables(conn);
            testAuctionUpdate(objectModel,persistence);

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
    @Test
    private static void testAuctionUpdate(ObjectModel objectModel,
                                          Persistence persistence) throws DTException {

        //Create registered user
        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);

        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(null);
        while(users.hasNext()){
            user1 = users.next();
        }

        //Create category
        Category category = objectModel.createCategory(null, "Game");
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        while(categories.hasNext()){
            category = categories.next();
        }

        //Create item
        Item item = objectModel.createItem(category, user1, "Monopoly", "Monopoly 20", "Cool Game");
        persistence.saveItem(item);

        Iterator<Item> items = objectModel.findItem(null);
        while(items.hasNext()){
            item = items.next();
        }
        System.out.println("Item has been created");


        //Create auction
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

        //Update auction


        auction.setMinPrice(14);
        persistence.saveAuction(auction);

        auctions = objectModel.findAuction(null);
        auctionCount = 0;
        while(auctions.hasNext()){
            auction = auctions.next();
            auctionCount++;
        }
        assertEquals(auctionCount,1);
        System.out.println("Auction found");

        //Check for update
        assertEquals(auction.getMinPrice(),14);
        System.out.println("Auction has been updated");

    }

    @Test
    private static void testAttributeUpdate(ObjectModel objectModel,
                                            Persistence persistence) throws DTException {

        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);
        //Create and restore user
        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(null);
        while(users.hasNext()){
            user1 = users.next();
        }
        System.out.println("User retrieved");

        //Create and restore category
        Category category = objectModel.createCategory(null, "Game");
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        while(categories.hasNext()){
            category = categories.next();
        }

        System.out.println("Category retrieved");

        //Create and restore item
        Item item = objectModel.createItem(category, user1, "Monopoly", "Monopoly 20", "Cool Game");
        persistence.saveItem(item);

        Iterator<Item> items = objectModel.findItem(null);
        while(items.hasNext()){
            item = items.next();
        }

        System.out.println("Item retrieved");

        //Create and restore attribute type
        AttributeType attributeType = objectModel.createAttributeType(category, "Games");
        persistence.saveAttributeType(attributeType);

        Iterator<AttributeType> attributeTypes = objectModel.getAttributeType(category);
        while(attributeTypes.hasNext()){
            attributeType = attributeTypes.next();
        }
        System.out.println("Attribute Type retrieved");

        //Create and restore attribute
        Attribute attribute = objectModel.createAttribute(attributeType, item, "1943");
        persistence.saveAttribute(attribute);

        Iterator<Attribute> attributes = objectModel.getAttribute(item);
        while(attributes.hasNext()){
            attribute = attributes.next();
        }
        System.out.println("Attribute restored");

        //Update attribute
        attribute.setValue("1954");
        persistence.saveAttribute(attribute);

        attributes = objectModel.getAttribute(item);
        while(attributes.hasNext()){
            attribute = attributes.next();
        }

        //Test update of value
        assertEquals(attribute.getValue(),"1954");

    }

    @Test
    private static void testItemUpdate(ObjectModel objectModel,
                                       Persistence persistence) throws DTException {

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

        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(null);
        while(users.hasNext()){
            user1 = users.next();
        }
        Item item = objectModel.createItem(category, user1, "Identifier", "1960 Knife", "Its old");
        persistence.saveItem(item);

        Iterator<Item> itemIter = objectModel.findItem(null);
        int itemCount =0;
        while(itemIter.hasNext()){
            itemCount ++;
            item = itemIter.next();
        }
        assertEquals(itemCount,1);
        System.out.println("Item saved");
        item.setDescription("new description");
        persistence.saveItem(item);

        Item item2 = objectModel.createItem();
        item2.setDescription("new description");
        itemIter = objectModel.findItem(item2);
        itemCount =0;
        while(itemIter.hasNext()){
            itemCount ++;
            item = itemIter.next();
        }
        assertEquals(itemCount,1);
        System.out.println("Item updated");



    }


    private static void testAttributeTypeUpdate(ObjectModel objectModel,
                                                Persistence persistence) throws DTException {
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
        attributeType.setName("Different Type");
        persistence.saveAttributeType(attributeType);
        attributeTypes = objectModel.getAttributeType(category);
        while(attributeTypes.hasNext()){
            attributeType = attributeTypes.next();

        }
        assertEquals(attributeType.getName(),"Different Type");
        System.out.println("AttributeType Changed");
    }


    private static void testCategoryUpdate(ObjectModel objectModel,
                                           Persistence persistence) throws DTException {
        Category category = null;
        persistence.saveCategory(category);
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount = 0;
        while(categories.hasNext()){
            category = categories.next();
            categoryCount++;
        }
        assertEquals(categoryCount,1);
        System.out.println("Category has been added.");
        category.setName("Something else");
        persistence.saveCategory(category);
        Category category2 = objectModel.createCategory();
        category2.setName("Something else");
        categories = objectModel.findCategory(category2);
        categoryCount =0;
        while(categories.hasNext()){
            category = categories.next();
            categoryCount++;

        }
        assertEquals(categoryCount,1);
        System.out.println("Category has been updated");

    }


    private static void testExperienceReport(ObjectModel objectModel,
                                             Persistence persistence) throws DTException {
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
        System.out.println("Users created");


        ExperienceReport report = objectModel.createExperienceReport(user1, user2, 4, "Cool time", new Date());
        persistence.saveExperienceReport(report);
        Iterator<ExperienceReport> reports = objectModel.findExperienceReport(null);
        int experienceReportCount = 0;

        while(reports.hasNext()){
            experienceReportCount ++;
            report = reports.next();
        }
        assertEquals(experienceReportCount,1);
        System.out.println("Experience report added.");
        report.setRating(1);
        persistence.saveExperienceReport(report);
        ExperienceReport reportCompare = objectModel.createExperienceReport();
        reportCompare.setRating(1);
        reports = objectModel.findExperienceReport(reportCompare);
        experienceReportCount = 0;
        while(reports.hasNext()){
            experienceReportCount ++;
        }
        assertEquals(experienceReportCount,1);
        System.out.println("Experience report has been changed");
    }


    @Test
    private static void testMembershipUpdate(ObjectModel objectModel,
                                             Persistence persistence) throws DTException {
        Membership membership = objectModel.createMembership(4, new java.util.Date());
        persistence.saveMembership(membership);
        membership = objectModel.findMembership();
        membership.setPrice(15);
        persistence.saveMembership(membership);
        membership.setPrice(0);
        membership = objectModel.findMembership();
        assertEquals(15,membership.getPrice());
        System.out.println("Membership has been updated");
    }


    @Test
    private static void testUserUpdate(ObjectModel objectModel,
                                       Persistence persistence) throws DTException {

        RegisteredUser user1 =objectModel.createRegisteredUser("matt", "Matt", "Lisivick", "hello", false, "email@uga.edu", "67893444323", false);
        persistence.saveRegisteredUser(user1);
        Iterator<RegisteredUser> users = persistence.restoreRegisteredUser(user1);
        while(users.hasNext()){

            user1 = users.next();

        }
        //Change user information
        user1.setName("Rick");
        user1.setPhone("8884442222");
        persistence.saveRegisteredUser(user1);

        //Create user for query
        RegisteredUser user2 = objectModel.createRegisteredUser();
        user2.setFirstName("Rick");
        user2.setPhone("8884442222");

        users = objectModel.findRegisteredUser(user2);
        int count =0;
        while(users.hasNext()){
            count++;
        }
        assertEquals(count,1);
        System.out.println("User has been updated");

    }

}