package edu.uga.dawgtrades.servlets;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;


/**
 * Created by Allen on 11/27/14.
 */
public class Logout extends javax.servlet.http.HttpServlet {
   

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    	
    	 HttpSession    httpSession = null;
         String         ssid = null;
         httpSession = request.getSession(false);
         if(httpSession!=null){
        	 
        	 ssid = (String) httpSession.getAttribute("ssid");
        	 if(ssid!=null){
        		 try{
        			 SessionManager.logout(ssid);
        			 httpSession.removeAttribute("ssid");
        			 httpSession.invalidate();
        			 System.out.println("Invalidated http session");
        		 		request.getRequestDispatcher("exit.ftl").forward(request, response);

        		 }catch(DTException e){
        			 e.printStackTrace();
        		 }
        		 
        	 }
	 		request.getRequestDispatcher("home.html").forward(request, response);

         }
         
         
    }
}
