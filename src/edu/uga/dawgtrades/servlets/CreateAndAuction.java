package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

public class CreateAndAuction extends HttpServlet {

	
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
				 RegisteredUser user = session.getUser();
				 String name = request.getParameter("name");
				 String identifier = request.getParameter("identifier");
				 String description = request.getParameter("description");
				 System.out.println(description);
				 String category_id = request.getParameter("category_id");
				 System.out.println(category_id);
				 int categoryId = Integer.parseInt(category_id);
				 Category modelCategory = session.getObjectModel().createCategory();
				 modelCategory.setId(categoryId);
				 Item itemToSave = session.getObjectModel().createItem();
				 itemToSave.setOwnerId(user.getId());
				 itemToSave.setName(name);
				 itemToSave.setDescription(description);
				 itemToSave.setIdentifier(identifier);
				 itemToSave.setCategoryId(categoryId);
				
				 
				 try {
					
					session.getObjectModel().storeItem(itemToSave);
					Iterator<Item> items = session.getObjectModel().findItem(itemToSave);
					while(items.hasNext()){
						itemToSave = items.next();
					}
					Iterator<AttributeType> attributeTypes = session.getObjectModel().getAttributeType(modelCategory);
					while(attributeTypes.hasNext()){
						AttributeType type = attributeTypes.next();
						String attributeString = request.getParameter(String.valueOf(type.getId()));
						Attribute attribute = session.getObjectModel().createAttribute();
						attribute.setAttributeType(type.getId());
						attribute.setItemId(itemToSave.getId());
						attribute.setValue(attributeString);
						session.getObjectModel().storeAttribute(attribute);
						
					}
					
					System.out.println("Item save & Attributes saved");
					request.setAttribute("item",itemToSave);
					request.setAttribute("message", "Auction your item here.");
	   	 			request.getRequestDispatcher("item_for_sale.ftl").forward(request, response);
				} catch (DTException e) {
					
					e.printStackTrace();
				}
				 
				 
				 
				 
			 }
	    }
	
	protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
			
		
	}
}
