package edu.uga.dawgtrades.servlets;

import javax.security.auth.login.Configuration;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;









import freemarker.template.*;
import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ExperienceReport;
import edu.uga.dawgtrades.model.Item;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;


/**
 * Created by Allen on 11/27/14.
 */
public class ReportTransaction extends javax.servlet.http.HttpServlet {


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
				 int reviewerId = Integer.parseInt(request.getParameter("reviewer"));
				 int revieweeId = Integer.parseInt(request.getParameter("reviewee"));
				 int rating = Integer.parseInt(request.getParameter("rating"));
				 String description = request.getParameter("description");
				 RegisteredUser modelUser = session.getObjectModel().createRegisteredUser();
				 modelUser.setId(reviewerId);
				 Iterator<RegisteredUser> userIter;
				try {
					userIter = session.getObjectModel().findRegisteredUser(modelUser);
				
				 RegisteredUser reviewer = null;
				 while(userIter.hasNext()){
					 reviewer = userIter.next();
				 }
				 modelUser.setId(revieweeId);
				 userIter = session.getObjectModel().findRegisteredUser(modelUser);
				 RegisteredUser reviewed = null;
				 while(userIter.hasNext()){
					 reviewed = userIter.next();
				 }
				 
				 ExperienceReport experienceReport = session.getObjectModel().createExperienceReport(reviewer, reviewed, rating, description, new Date());
				 session.getObjectModel().storeExperienceReport(experienceReport);
				 request.getRequestDispatcher("feedBack.ftl").forward(request, response);;
				 
				} catch (DTException e) {
					// TODO Auto-generated catch block
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
    					 
    					 int item_id = Integer.parseInt(request.getParameter("item_id"));
    					 System.out.println(item_id);
    					 Item modelItem = session.getObjectModel().createItem();
    					 modelItem.setId(item_id);
    					 try {
    						Item ratedItem = null;
							Iterator<Item> items = session.getObjectModel().findItem(modelItem);
							while(items.hasNext()){
								
								ratedItem = items.next();
								System.out.println(ratedItem.getName());
							}
							
							Auction ratedAuction = session.getObjectModel().getAuction(ratedItem);
							Bid highestBid = SessionManager.getHighestBidForAuction(session, ratedAuction);
							
							if(ratedItem.getOwnerId()==session.getUser().getId()){
								
								request.setAttribute("reviewer", session.getUser().getId());
								request.setAttribute("reviewee", highestBid.getRegisteredUser().getId());
							}else{
								request.setAttribute("reviewer", highestBid.getRegisteredUser().getId());
								request.setAttribute("reviewee", ratedItem.getOwnerId());
								
							}
							request.setAttribute("message", "How was your transaction?");
							request.getRequestDispatcher("report_transaction.ftl").forward(request, response);;
					
							
							
    					 } catch (DTException e) {

							e.printStackTrace();
						}
    					 
    				 }
    				 
    	
    	
    
}
}
