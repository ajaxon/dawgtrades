package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
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
				 String category_id = request.getParameter("category");
				 int categoryId = Integer.parseInt(category_id);
				 Item itemToSave = session.getObjectModel().createItem();
				 itemToSave.setOwnerId(user.getId());
				 itemToSave.setName(name);
				 itemToSave.setDescription(description);
				 itemToSave.setIdentifier(identifier);
				 itemToSave.setCategoryId(categoryId);
				 try {
					session.getObjectModel().storeItem(itemToSave);
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
					 try {
						 Iterator<Category> categoryIter = session.getObjectModel().findCategory(null);
						 
						
						 
						 List<Category> categories = new ArrayList<Category>();
						 while(categoryIter.hasNext()){
							 categories.add(categoryIter.next());
							 
						 }
						 request.setAttribute("categories",categories);
						 request.getRequestDispatcher("create_item.ftl").forward(request, response);
					 }catch(DTException e){
						 e.printStackTrace();
					 }
					 
				 }
	    }
}
