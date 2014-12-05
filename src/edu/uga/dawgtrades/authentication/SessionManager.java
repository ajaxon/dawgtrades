package edu.uga.dawgtrades.authentication;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

public class SessionManager {

	
	private static Map<String,Session> sessions;
	
	private static Map<String,Session> loggedIn;
	
	static{
		sessions = new HashMap<String,Session>();
		loggedIn = new HashMap<String,Session>();
		
	}
	
	public static String login(String username, String password) throws DTException{
		
		Connection conn = null;
		ObjectModel objectModel = null;
		Persistence persistence = null;
		RegisteredUser loginUser = null;
		RegisteredUser knownUser = null;
		Session s = null;
		
		try{
			conn = DbUtils.connect();
			
			
		}catch(Exception seq){
			throw new DTException("SessionManager.login: Unable to get DB connection");
		}
		
		s = new Session( conn);
		objectModel = new ObjectModelImpl();
		persistence = new PersistenceImpl(conn,objectModel);
		loginUser = objectModel.createRegisteredUser();
		loginUser.setName(username);
		loginUser.setPassword(password);
		
		Iterator<RegisteredUser> users = persistence.restoreRegisteredUser(knownUser);
		if(users.hasNext()){
			knownUser = users.next();
			System.out.println("Have user");
			System.out.println("User"+knownUser.getId());
			
			
		
		
		loginUser = null;
		
		return createSession(s,knownUser);
		
		}else{
			
			throw new DTException("SessionManager.login: Invalid User Name or Password");
		}
	}
	
	private static String createSession(Session session, RegisteredUser user) throws DTException{
		
		if(user==null){
			if(session.getConnection()!=null){
				try{
					
					session.getConnection().close();
				}
				catch(SQLException sqlEx){
					throw new DTException("SessionManager.createSession: No user found"+sqlEx);
				}
				
			}else{
				return "No connection to DB";
			}
		}
		if(loggedIn.containsKey(user.getName())){
			Session qs = loggedIn.get(user.getName());
			qs.setUser(user);
			return qs.getSessionId();
			
		}
		session.setUser(user);
		String ssid = secureHash("TRADE"+System.nanoTime());
		session.setSessionId(ssid);
		sessions.put(ssid, session);
		loggedIn.put(user.getName(), session);
		session.start();
		
		
		return ssid;
	}
	public static void logout(Session s) throws DTException{
		s.setExpiration(new Date());
		s.interrupt();
		removeSession(s);
	}
	
	
	public static void removeSession(Session s) throws DTException {

		try{
			s.getConnection().close();
			
		}catch(SQLException sqe){
			throw new DTException("SessionManager.removeSession: Cannot close connection");
			
		}
		sessions.remove(s.getSessionId());
		loggedIn.remove(s.getUser().getName());
	}
	
	public static Session getSessionById(String ssid){
		return sessions.get(ssid);
	}
	
	
	  public static String secureHash( String input ) 
	            throws DTException
	    {
	        StringBuilder output = new StringBuilder();
	        try {
	            MessageDigest md = MessageDigest.getInstance( "SHA" );
	            md.update( input.getBytes() );
	            byte[] digest = md.digest();
	            for( int i = 0; i < digest.length; i++ ) {
	                String hex = Integer.toHexString( digest[i] );
	                if( hex.length() == 1 )
	                    hex = "0" + hex;
	                hex = hex.substring( hex.length() - 2 );
	                output.append( hex );
	            }
	        }
	        catch( Exception e ) {
	          
	            throw new DTException(
	                    "SessionManager.secureHash: Invalid Encryption Algorithm" );
	        }
	        return output.toString();
	    }
}
