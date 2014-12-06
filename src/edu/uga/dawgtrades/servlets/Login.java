package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;





import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

/**
 * Created by Allen on 11/27/14.
 */
public class Login extends javax.servlet.http.HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    	//Session Login
    	HttpSession httpSession = null;
    	String      ssid = null;
    	Session session = null;
    	String username = null;
    	String password = null;
    	RegisteredUser user = null;
    
    	 httpSession = request.getSession();
    	 if(httpSession.getAttribute("ssid")!=null){
    		 
    		 ssid = (String) httpSession.getAttribute( "ssid" );
         
    	 }
      
         
    	
    	
    	 username = (String)request.getParameter("username");
    	 
    	 password = (String)request.getParameter("password");
    	 
    	 if(username == null || password == null){
    		 request.setAttribute("username","Cannot be null");
    		 
    	 }
    	 
    	 try{
    		 System.out.println("Trying to obtain ssid");
    		 ssid = SessionManager.login(username, password);
    		 System.out.println("Obtained ssid:"+ssid);
    		 httpSession.setAttribute("ssid", ssid);
    		 session = SessionManager.getSessionById(ssid);
    		 System.out.println("Connection:"+session.getConnection());
    		 user = session.getUser();
    		 
    		 
    	 }catch(Exception e){
    		 try {
				throw new DTException("Login.error"+e);
			} catch (DTException e1) {

				e1.printStackTrace();
			}
    	 }

    	 if(user!=null){
    		 request.setAttribute("user", user);
    		 request.getRequestDispatcher("index.ftl").forward(request, response);
			 
			 
		 }else{
			 
			 
 
 	    	request.getRequestDispatcher("home.html").forward(request, response);
			 
		 }
		 
    	 }
	
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    	HttpSession httpSession = null;
    	String ssid = null;
    	Session session = null;
    	RegisteredUser user = null;
    			
    	httpSession = request.getSession();
   	 	if(httpSession.getAttribute("ssid")!=null){
   		 
   	 		ssid = (String) httpSession.getAttribute( "ssid" );
   	 		session = SessionManager.getSessionById(ssid);
   	 		if(session==null){
   	 			
   	 			request.getRequestDispatcher("home.html").forward(request, response);

   	 		}else{
   	 			
   	 			user = session.getUser();
   	 			request.setAttribute("user", user);
   	 			request.getRequestDispatcher("index.ftl").forward(request, response);;
   	 			
   	 		}
   	 }else{
   		 request.getRequestDispatcher("home.html").forward(request, response);
   	 }
    }
}
