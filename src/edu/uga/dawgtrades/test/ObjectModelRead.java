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
import sun.jvm.hotspot.asm.Register;

/**
 * Created by Allen on 11/16/14.
 */
public class ObjectModelRead extends TestCase
{
    static Connection  conn = null;
    static ObjectModel objectModel = null;
    static Persistence persistence = null;
    Category category =  null;
    RegisteredUser user = null;
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


        // Get a category
        Category model = objectModel.createCategory();
        model.setName("Computers");
        Iterator<Category> categories = objectModel.findCategory(model);
        while(categories.hasNext()){
            category = categories.next();
        }
        //

    }



    @Test
    public void testcheckAllItems() throws DTException {

            Iterator<Item> items = objectModel.findItem(null);
        int itemcount =0;
       while(items.hasNext()){
           Item item = items.next();
           itemcount++;
       }
        assertEquals(itemcount,2);


    }
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
    public void test_getItemsByUser() throws DTException {
        RegisteredUser model = objectModel.createRegisteredUser();
        model.setName("testName");
        Iterator<RegisteredUser> users = objectModel.findRegisteredUser(model);
        int itemsCount = 0;
        RegisteredUser user=null;
        while(users.hasNext()){
            user = users.next();
        }
        Iterator<Item> items = objectModel.getItem(user);
        while(items.hasNext()){
            Item item = items.next();
            itemsCount++;
        }
        assertEquals(1,itemsCount);

    }
    @Test
    public void testgetAllCategories() throws DTException {
        Iterator<Category> categories = objectModel.findCategory(null);
        int categoryCount = 0;
        while(categories.hasNext()){
            Category cat = categories.next();
            categoryCount++;
        }
        assertEquals(3,categoryCount);
    }
    @Test
    public void test_getItemsByCategory() throws DTException {
        Iterator<Item> items = objectModel.getItem(category);
        while(items.hasNext()){
            Item item = items.next();
            System.out.println(item);
        }
    }

    @AfterClass
    public void teardown() throws SQLException {
        conn.close();

}
}