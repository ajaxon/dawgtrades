package edu.uga.dawgtrades.authentication;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;




import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;

public class Session extends Thread {
	
	
	private Connection conn;
    private ObjectModel objectModel;
    private RegisteredUser registeredUser ;
    private String id;
    private Date expiration;



    public Session( Connection conn )
    {
    	this.conn = conn;
    	objectModel = new ObjectModelImpl();
    	Persistence persistence = new PersistenceImpl( conn, objectModel ); 
    	objectModel.setPersistence( persistence ); 
    	   extendExpiration();
    }

    public Connection getConnection()
    {
    		  extendExpiration();
    	return conn;
    }
    
    public RegisteredUser getUser()
    {
        extendExpiration();
        return registeredUser;
    }
    
    public void setUser(RegisteredUser registeredUser) throws DTException{
    	
    	extendExpiration();
    	this.registeredUser=registeredUser;
    	
    }
    public String getSessionId(){
    	extendExpiration();
    	return id;
    	
    }
    
    public void setSessionId(String id){
    	this.id=id;
    }
    
    public void setExpiration(Date expiration){
    	
    	this.expiration=expiration;
    	
    }
    
    public Date getExpiration(){
    	
    	return expiration;
    }
    
    private void extendExpiration(){
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.MINUTE, 30);
    	this.expiration = c.getTime();
    	
    }
    
    public ObjectModel getObjectModel(){
    	
    	
    	return objectModel;
    }
    
    @Override
    public void run(){
    	
    	long diff = expiration.getTime() - System.currentTimeMillis();
    	while(diff>0){
    		try{
    			
    			sleep(diff);
    		}catch(Exception e){
    			
    			break;
    		}
    		diff = expiration.getTime() - System.currentTimeMillis();
    		
    		
    	}
    	try{
    	SessionManager.removeSession(this);
    	}catch(DTException e){
    		
    		try{
    			
    			throw e;
    		}catch(DTException e1){
    			e1.printStackTrace();
    		}
    		
    	}
    }
    
    
    
    
}