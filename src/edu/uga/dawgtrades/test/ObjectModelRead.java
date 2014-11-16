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
public class ObjectModelRead
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

        try {

            // Retrieve all existing RegisteredUser objects
            System.out.println( "Club objects:" );
            Iterator<RegisteredUser> userIter = objectModel.findRegisteredUser( null );
            while( userIter.hasNext() ) {
                RegisteredUser u = userIter.next();
                System.out.println( u );

            }



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