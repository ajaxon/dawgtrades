package edu.uga.dawgtrades.test;
import java.sql.Connection;
import java.util.Iterator;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
/**
 * Created by Allen on 11/16/14.
 */
public class ObjectModelUpdate
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

        try {

            RegisteredUser  bob = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setName( "bob" );
            userIter = objectModel.findRegisteredUser( modelUser);
            while( userIter.hasNext() ) {
                bob = userIter.next();
                System.out.println( bob );

            }

            // modify the name of the Tennis club to Advanced Tennis
            bob.setName( "Mike" );
            objectModel.storeRegisteredUser( bob );
            System.out.println( "Updated the name bob to Mike" );


            // modify the name of the Tennis club to "Advanced Tennis"
            bob.setPhone( "(111) 123-4567" );
            bob.setCanText(false);
            bob.setLastName("newLastname");
            objectModel.storeRegisteredUser( bob );
            System.out.println( "Updated the phone number , cantext, lastname" );

        }
        catch( DTException ce)
        {
            System.err.println( "ClubsException: " + ce );
        }
        catch( Exception e)
        {
            System.err.println( "Exception: " + e );
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