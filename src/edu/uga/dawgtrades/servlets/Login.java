package edu.uga.dawgtrades.servlets;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

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
    	HttpSession httpSession;
    	String      ssid = null;
    	Session session = null;
    	String username = null;
    	String password = null;
    	RegisteredUser user = null;
    	
    	 httpSession = request.getSession();
         ssid = (String) httpSession.getAttribute( "ssid" );
         if(ssid!=null){
        	 System.out.println("Already have ssid:"+ssid);
        	 session = SessionManager.getSessionById(ssid);
        	 
         }else{
        	 request.setAttribute("username","ssid is null");
         }
         
    	
    	
    	 username = request.getParameter("username");
    	 password = request.getParameter("password");
    	 if(username == null || password == null){
    		 request.setAttribute("username","Cannot be null");
    		 
    	 }
    	 
    	 try{
    		 ssid = SessionManager.login(username, password);
    		 httpSession.setAttribute("ssid",ssid);
    		 session = SessionManager.getSessionById(ssid);
    		 
    	
    		
   
    		 
    	 }catch(Exception e){
    		 try {
				throw new DTException("Login.error");
			} catch (DTException e1) {

				e1.printStackTrace();
			}
    	 }
    	 
    	if(!session.isAlive()){
    		request.setAttribute("user", "session not started");
    		
    	}else{
    	request.setAttribute("username", session.getUser().getName());
    	request.setAttribute("password", session.getUser().getLastName());
    	}
    	request.getRequestDispatcher("test.ftl").forward(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
