package edu.uga.dawgtrades.test;

import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
import edu.uga.dawgtrades.persist.impl.RegisteredUserIterator;
import sun.jvm.hotspot.asm.Register;


public class ObjectModelDelete
{
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

        Iterator<RegisteredUser> userIter = null;
        Iterator<RegisteredUser> user2Iter = null;
        Iterator<Category> categoryIterator = null;
        Iterator<Item> itemIterator = null;
        try {


            RegisteredUser joeJohnson = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setFirstName("joe");
            modelUser.setLastName("johnson");

            userIter = objectModel.findRegisteredUser( modelUser );
            while( userIter.hasNext() ) {
                joeJohnson = userIter.next();

                }


            // delete joe Johnson
            if( joeJohnson != null ) {
                System.out.println(joeJohnson.getId());
                objectModel.deleteRegisteredUser( joeJohnson );
                System.out.println( "Deleted user Joe Johnson" );
            }
            else
                System.out.println( "Failed to find Joe Johnson" );
            // delete all users
            RegisteredUser user = null;

            user2Iter = objectModel.findRegisteredUser(null);
            while(user2Iter.hasNext()){
                user = user2Iter.next();
                objectModel.deleteRegisteredUser(user);
            }
            // delete all categories
            Category category = null;
            categoryIterator = objectModel.findCategory(null);
            while(categoryIterator.hasNext()){
                category = categoryIterator.next();
                objectModel.deleteCategory(category);
            }
            // delete all items
            Item item = null;
            itemIterator = objectModel.findItem(null);
            while(itemIterator.hasNext())
            {
                item = itemIterator.next();
                objectModel.deleteItem(item);
            }




        }
        catch( DTException ce )
        {
            System.err.println( "ClubsException: " + ce );
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
