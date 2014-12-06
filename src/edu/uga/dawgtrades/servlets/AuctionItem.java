package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

/**
 * Created by Allen on 11/27/14.
 */
public class AuctionItem extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
			 
			
			 int itemId = Integer.parseInt(request.getParameter("item_id"));
			 float minPrice = Float.parseFloat(request.getParameter("minPrice"));
			 Item item = session.getObjectModel().createItem();
			 item.setId(itemId);
			
			try {
				 Iterator<Item> items = session.getObjectModel().findItem(item);
				 int count = 0;
				 while(items.hasNext()){
					 item = items.next();
					 count++;
				 }
				 System.out.println(count);
				 Auction auctionToSave;
				auctionToSave = session.getObjectModel().createAuction(item,minPrice,new Date());				
				session.getObjectModel().storeAuction(auctionToSave);
				request.getRequestDispatcher("login").forward(request,response);
			} catch (DTException e1) {
				e1.printStackTrace();
			}
			
		 }
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
				 RegisteredUser user = session.getUser();
				 try {
					Iterator<Item> items = session.getObjectModel().getItem(user);
					
					List<Item> itemList = new ArrayList<Item>();
					while(items.hasNext()){
						System.out.println("Item found");
						Item item = items.next();
						if(session.getObjectModel().getAuction(item)==null){
							itemList.add(item);
						}
						
				
					}
					request.setAttribute("items", itemList);
					request.getRequestDispatcher("auction_item.ftl").forward(request, response);
					
				} catch (DTException e) {
					e.printStackTrace();
				}
				 
			 }
    }
}
