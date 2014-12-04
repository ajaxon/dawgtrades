package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
import junit.framework.TestCase;

public class ObjectModelUpdate extends TestCase {

	

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
    AttributeType attributeType = null;
    ExperienceReport experienceReport = null;
    @BeforeClass
    public void setUp() throws DTException {


        // get a database connection
        try {
            conn = DbUtils.connect();
        }
        catch (Exception seq) {
            System.err.println( "ObjectModelUpdate: Unable to obtain a database connection" + seq.toString());
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
        attributeType = getAttributeType();
        experienceReport = getExperienceReport();
        //

    }

    public AttributeType getAttributeType() throws DTException {
        AttributeType attribute_type = null;
        Iterator<AttributeType> attrs = objectModel.getAttributeType(category);
        while(attrs.hasNext()){
            attribute_type = attrs.next();
            if(attribute_type.getName()=="Brand")
                break;

        }
        return attribute_type;

    }

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
    
    public ExperienceReport getExperienceReport() throws DTException {
    	
    	ExperienceReport modelExperienceReport = objectModel.createExperienceReport();
    	Iterator<ExperienceReport> experienceReports = objectModel.findExperienceReport(modelExperienceReport);
    	while(experienceReports.hasNext()){
    		experienceReport = experienceReports.next();
    	}
    	return experienceReport;
    	
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
    @Test
   public void test_update_first_name_User() throws DTException{
    	RegisteredUser tempUser = user;
    	user.setFirstName("ChangeName");
    	persistence.saveRegisteredUser(user);
    	RegisteredUser userTest = objectModel.createRegisteredUser();
    	userTest.setFirstName("ChangeName");
    	Iterator<RegisteredUser> users = objectModel.findRegisteredUser(userTest);
    	int count =0;
    	while(users.hasNext()){
    		users.next();
    		count++;
    	}
    	assertEquals(1,count);
    	//User returned to original information
    	persistence.saveRegisteredUser(tempUser);
    	System.out.println("User First Name Updated Correctly");
    	
    	
    }
    
    @Test
    public void test_update_last_name_User() throws DTException{
     	RegisteredUser tempUser = user;
     	user.setLastName("ChangeName");
     	persistence.saveRegisteredUser(user);
     	RegisteredUser userTest = objectModel.createRegisteredUser();
     	userTest.setLastName("ChangeName");
     	Iterator<RegisteredUser> users = objectModel.findRegisteredUser(userTest);
     	int count =0;
     	while(users.hasNext()){
     		users.next();
     		count++;
     	}
     	assertEquals(1,count);
     	//User returned to original information
     	persistence.saveRegisteredUser(tempUser);
    	System.out.println("User Last Name Updated Correctly");

     	
     }
    
    @Test
    public void test_update_password_User() throws DTException{
     	RegisteredUser tempUser = user;
     	user.setPassword("ChangeName");
     	persistence.saveRegisteredUser(user);
     	RegisteredUser userTest = objectModel.createRegisteredUser();
     	userTest.setPassword("ChangeName");
     	Iterator<RegisteredUser> users = objectModel.findRegisteredUser(userTest);
     	int count =0;
     	while(users.hasNext()){
     		users.next();
     		count++;
     	}
     	assertEquals(1,count);
     	//User returned to original information
     	persistence.saveRegisteredUser(tempUser);
    	System.out.println("User Password Updated Correctly");

     	
     }
    
    @Test
    public void test_update_description_Item() throws DTException{
    	Item itemTemp = item;
    	item.setDescription("ChangeItem");
    	persistence.saveItem(item);
    	Item itemTest = objectModel.createItem();
    	itemTest.setDescription("ChangeItem");
    	Iterator<Item> items = objectModel.findItem(itemTest);
    	int count = 0;
    	while(items.hasNext()){
    		items.next();
    		count++;
    	}
    	assertEquals(1,count);
    	persistence.saveItem(itemTemp);
    	System.out.println("Item Description Updated Correctly");

    }

    
    @Test
    public void test_update_identifier_Item() throws DTException{
    	Item itemTemp = item;
    	item.setIdentifier("ChangeItem");
    	persistence.saveItem(item);
    	Item itemTest = objectModel.createItem();
    	itemTest.setIdentifier("ChangeItem");
    	Iterator<Item> items = objectModel.findItem(itemTest);
    	int count = 0;
    	while(items.hasNext()){
    		items.next();
    		count++;
    	}
    	assertEquals(1,count);
    	persistence.saveItem(itemTemp);
    	System.out.println("Item Identifier Updated Correctly");

    }
//    
    
    @Test
    public void test_update_Category() throws DTException{
    	Category categoryTemp = category;
    	category.setName("ChangeName");
    	persistence.saveCategory(category);
    	Category categoryTest = objectModel.createCategory();
    	categoryTest.setName("ChangeName");
    	Iterator<Category> categories = objectModel.findCategory(categoryTest);
    	int count = 0;
    	while(categories.hasNext()){
    		count++;
    		categories.next();
    	}
    	assertEquals(1,count);
    	persistence.saveCategory(categoryTemp);
    	System.out.println("Category Name Updated Correctly");

    	
    	
    }
    @Test
    public void test_update_name_AttributeType() throws DTException{
    	AttributeType typeTemp = attributeType;
    	attributeType.setName("ChangeName");
    	persistence.saveAttributeType(attributeType);
    	AttributeType type = objectModel.createAttributeType();
    	type.setName("ChangeName");
    	Iterator<AttributeType>  attributeTypes = objectModel.getAttributeType(category);
    	boolean found = false;
    	while(attributeTypes.hasNext()){
    		type = attributeTypes.next();
    		if(type.getName().equals("ChangeName")){
    			found = true;
    		}
    		
    	}
    	assertTrue(found); 
    	persistence.saveAttributeType(typeTemp);
    	System.out.println("AttributeType Name Updated Correctly");
    	  	
    }
//    @Test
//    public void test_update_name_Auction() throws DTException{
//    	Auction auctionTemp = auction;
//    	System.out.println("Auction Id is "+auction.getId());
//    	if(auction.getExpiration()==null){
//    		System.out.println("Auction Expiration is null");
//    	}
//    	assertTrue(auction.getExpiration()!=null);
//    	auction.setMinPrice(50f);
//    	persistence.saveAuction(auction);
//    	Auction auctionTest = objectModel.createAuction();
//    	auctionTest.setMinPrice(50f);
//    	Iterator<Auction> auctions = objectModel.findAuction(auctionTest);
//    	int count = 0;
//    	while(auctions.hasNext()){
//    		auctions.next();
//    		count ++;
//    		
//    		
//    	}
//    	assertEquals(1,count);
//    	persistence.saveAuction(auctionTemp);
//    	System.out.println("Auction Price Updated Correctly");
//    }
   
    @Test
    public void test_update_price_Membership() throws DTException {


        Membership membership = objectModel.findMembership();
        Membership membershipTemp = membership;
        membership.setPrice(7);
        persistence.saveMembership(membership);
        membership = objectModel.findMembership();
        assertEquals(7.0f,membership.getPrice());
        persistence.saveMembership(membershipTemp);
        System.out.println("Membership price updated correctly");
    }
    
//    @Test 
//    public void test_update_experienceReport() throws DTException {
//    	ExperienceReport tempReport = experienceReport;
//    	experienceReport.setRating(1);
//    	persistence.saveExperienceReport(experienceReport);
//    	ExperienceReport expTest = objectModel.createExperienceReport();
//    	expTest.setRating(1);
//    	Iterator<ExperienceReport> reports = objectModel.findExperienceReport(expTest);
//    	int count = 0;
//    	while(reports.hasNext()){
//    		reports.next();
//    		count++;
//    		
//    	}
//    	assertEquals(1,count);
//    	persistence.saveExperienceReport(tempReport);
//    	System.out.println("ExperienceReport rating updated correctly");
//    }

    @AfterClass
    public void teardown() throws SQLException {
        conn.close();

}
}
