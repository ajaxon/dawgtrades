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

        try {


            RegisteredUser joeJohnson = null;
            RegisteredUser modelUser = objectModel.createRegisteredUser();
            modelUser.setFirstName( "joe" );
            modelUser.setLastName( "johnson" );

            userIter = objectModel.findRegisteredUser( modelUser );
            while( userIter.hasNext() ) {
                joeJohnson = userIter.next();
                System.out.println( joeJohnson );




                }


            // delete joe Johnson
            if( joeJohnson != null ) {
                objectModel.deleteRegisteredUser( joeJohnson );
                System.out.println( "Deleted user Joe Johnson" );
            }
            else
                System.out.println( "Failed to find Joe Johnson" );




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
