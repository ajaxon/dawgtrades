package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

public class ItemToAuction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
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
			 
			 if(request.getParameter("item_id")!=null){
			 	int itemId = Integer.parseInt(request.getParameter("item_id"));
			 	Item modelItem = session.getObjectModel().createItem();
			 	modelItem.setId(itemId);
			 	try{
			 	Iterator<Item> items = session.getObjectModel().findItem(modelItem);
			 	Item itemForSale = null;
			 	while(items.hasNext()){
			 		itemForSale = items.next();
			 	}
			 	
			 	
			 	request.setAttribute("item", itemForSale);
			 	
			 	request.setAttribute("message", "Create a new auction here.");
			 	request.getRequestDispatcher("item_for_sale.ftl").forward(request, response);
			 	
			 	}catch(DTException e){
			 		
			 		e.printStackTrace();
			 	}
			 	}else{
			 		
			 		
			 	}
			 
		 }
	}


}

