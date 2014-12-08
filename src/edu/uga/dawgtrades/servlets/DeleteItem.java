package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

/**
 * Created by Allen on 11/27/14.
 */
public class DeleteItem extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    	HttpSession httpSession = null;
    	String      ssid = null;
    	Session session = null;
    	
    	
    	httpSession = request.getSession();
    	
    	if(httpSession.getAttribute("ssid")!=null){
    		
    		ssid = (String) httpSession.getAttribute("ssid");
    		
    	}else{
    		System.out.println("No ssid found");
 	    	request.getRequestDispatcher("home.html").forward(request, response);

    	}
    		
    	
		 session = SessionManager.getSessionById(ssid);
		 if(session==null){
			 
	 	    	request.getRequestDispatcher("home.html").forward(request, response);
	 	    	System.out.println("No session found");
		 }else{
			 String item_id = request.getParameter("item_id");
			 int itemId = Integer.parseInt(item_id);
			 Item item = session.getObjectModel().createItem();
			 item.setId(itemId);
			 try {
				Iterator<Item> items = session.getObjectModel().findItem(item);
				Item deleteItem = null;
				while(items.hasNext()){
					
					deleteItem = items.next();
					
				}
				session.getObjectModel().deleteItem(deleteItem);
				request.getRequestDispatcher("item_deleted.ftl").forward(request, response);;

				
			 } catch (DTException e) {

				e.printStackTrace();
			}
			 
			 
			 
    	
    	
		 }
    }
}
