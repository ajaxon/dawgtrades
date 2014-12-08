package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Attribute;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.RegisteredUser;

public class CreateItem extends javax.servlet.http.HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
					
					
					request.setAttribute("user", user);
	   	 			request.getRequestDispatcher("index.ftl").forward(request, response);
				} catch (DTException e) {
					
					e.printStackTrace();
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
					 
					 if(request.getParameter("categoryID")!=null){
					 
							 try {
								 int categoryID = Integer.parseInt(request.getParameter("categoryID"));
								 
								 Category modelCategory = session.getObjectModel().createCategory();
								 modelCategory.setId(categoryID);
								 Iterator<Category> categoryIter = session.getObjectModel().findCategory(modelCategory);
								 
								
								 
								Category category = null;
								 while(categoryIter.hasNext()){

									 category = categoryIter.next();
									 
								 }
								 request.setAttribute("category",category);
								 
								 Iterator<AttributeType> attributeTypeIter = session.getObjectModel().getAttributeType(category);								 
								 List<AttributeType> attributeTypes = new ArrayList<AttributeType>();
								 while(attributeTypeIter.hasNext()){
									 attributeTypes.add(attributeTypeIter.next());
									 
								 }
								 request.setAttribute("attribute_types", attributeTypes);
								 request.getRequestDispatcher("create_item.ftl").forward(request, response);
							 }catch(DTException e){
								 e.printStackTrace();
							 }
					 } 
				 }
	    }
}
