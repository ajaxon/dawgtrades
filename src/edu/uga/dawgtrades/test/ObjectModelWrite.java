package edu.uga.dawgtrades.test;

import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


import java.sql.Connection;
import java.util.Date;





import java.sql.Connection;
import java.util.Date;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.Membership;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


// This is a simple class to test the creation of the entity classes
// and associations in the Clubs example.
//
public class ObjectModelWrite
{
    public static void main(String[] args)
    {
        Connection conn = null;
        ObjectModel objectModel = null;
        Persistence persistence = null;

        RegisteredUser bob;
        RegisteredUser        joe;
        RegisteredUser        dan;
        RegisteredUser        tom;

        Membership membership;

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

        //objectModel.setPersistence( persistence );

        try {

            // create a few people
            joe = objectModel.createRegisteredUser( "joe", "joe", "johnson", "password", true, "email@emailtest.com", "343-3232",false );
            dan = objectModel.createRegisteredUser( "mary", "marypass", "maryLastname", "Marypass", false, "dan@emailtest.com", "444-9876",true );
            bob = objectModel.createRegisteredUser( "bob", "bobpass", "bobLastname", "Robertpass", false, "bob@emailtest.com", "567-7788",true );
            tom = objectModel.createRegisteredUser( "julie", "julie", "julieLastname", "Juliepass", false, "tom@emailtest.com", "364-7592",true );

            persistence.saveRegisteredUser( joe );
            persistence.saveRegisteredUser( dan );
            persistence.saveRegisteredUser( bob );
            persistence.saveRegisteredUser( tom );

            /*

            bridge = objectModel.createClub( "Bridge", "33 Leaf St., Blossom, OR. 88888", new Date(), joe );
            persistence.saveClub( bridge );

            chess = objectModel.createClub( "Chess", "734 Pine Straw Dr., Bloom, KY. 48878", new Date(), mary );
            persistence.saveClub( chess );

            tennis = objectModel.createClub( "Tennis", "333 Wide St., Flower, RI. 17345", new Date(), mary );
            persistence.saveClub( tennis );

            running = objectModel.createClub( "Running", "445 Pace St., Quicker, Wy. 77546", new Date(), bob );
            persistence.saveClub( running );

            membership = objectModel.createMembership( joe, bridge, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( bob, bridge, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( heather, bridge, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( mary, chess, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( mary, tennis, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( julie, tennis, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( bob, tennis, new Date() );
            persistence.saveMembership( membership );

            membership = objectModel.createMembership( joe, chess, new Date() );
            persistence.saveMembership( membership );
            */
            System.out.println( "Entity objects created and saved in the persistence module" );

        }
        catch( DTException ce) {
            System.err.println( "Exception: " + ce );
            ce.printStackTrace();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        finally {
            // close the connection
            try {
                conn.close();
            }
            catch( Exception e ) {
                System.err.println( "Exception: " + e );
                e.printStackTrace();
            }
        }
    }
}