package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.swing.text.DateFormatter;

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
			 float minPrice = -1000;
			
			 int itemId = Integer.parseInt(request.getParameter("item_id"));
			try {
				Item item = session.getObjectModel().createItem();
				item.setId(itemId);
				String month =request.getParameter("month_start");
				String day = request.getParameter("day_start");
				String year= request.getParameter("year_start");
				String time =request.getParameter("time");
				
				String date =month+" "+day+", "+year+" "+time;
				Date dateAuctionEnd = null;
				
				 DateFormat format = 
				            DateFormat.getDateTimeInstance(
				            DateFormat.MEDIUM, DateFormat.SHORT);
				try {
					 dateAuctionEnd = format.parse(date);
					System.out.println(dateAuctionEnd.toString());
					
				} catch (ParseException e) {

					e.printStackTrace();
				}
				try{
			 
						minPrice = Float.parseFloat(request.getParameter("minPrice"));
			 
			 
					}catch(NumberFormatException e){
				 
						Iterator<Item> items = session.getObjectModel().findItem(item);
						Item itemForSale = null;
						while(items.hasNext()){
							itemForSale = items.next();
						}
				 	
				 	
					request.setAttribute("item", itemForSale);
				 	
				 	request.setAttribute("message", "Invalid starting price. Please ensure you have entered in a proper numberic number");
				 	request.getRequestDispatcher("item_for_sale.ftl").forward(request, response);
				 	return;
			 }
				
				 Calendar cal = Calendar.getInstance(); // creates calendar
				    cal.setTime(new Date()); // sets calendar time/date
				    cal.add(Calendar.HOUR_OF_DAY, 1); //adds one hour
				    System.out.println(cal.getTime().toString());
				    
			 
				if(dateAuctionEnd.before(cal.getTime())){
						Iterator<Item> items = session.getObjectModel().findItem(item);
						Item itemForSale = null;
						while(items.hasNext()){
							itemForSale = items.next();
						}
						
			 	
						request.setAttribute("item", itemForSale);
			 	
						request.setAttribute("message", "Sorry, but the input ending date occurs before now or too soon to auction item.");
						request.getRequestDispatcher("item_for_sale.ftl").forward(request, response);
						return;
					
				}
				
				 Iterator<Item> items = session.getObjectModel().findItem(item);
				 int count = 0;
				 while(items.hasNext()){
					 item = items.next();
					 count++;
				 }
				 System.out.println(count);
				 Auction auctionToSave;
				auctionToSave = session.getObjectModel().createAuction(item,minPrice,dateAuctionEnd);				
				session.getObjectModel().storeAuction(auctionToSave);
				RegisteredUser user =session.getUser();
				request.setAttribute("user", user);
   	 			request.getRequestDispatcher("itemAuctioned.ftl").forward(request, response);
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
				 HashMap<String,String> itemStatus = new HashMap<String,String>();
				 
				 RegisteredUser user = session.getUser();
				 try {
					Iterator<Item> items = session.getObjectModel().getItem(user);
					
					List<Item> itemList = new ArrayList<Item>();
					while(items.hasNext()){
						System.out.println("Item found");
						Item item = items.next();
						if(session.getObjectModel().getAuction(item)==null){
							itemList.add(item);
							itemStatus.put(item.getName(), "no_auction");
						}else{
							Auction auction = session.getObjectModel().getAuction(item);
							if(auction.getIsClosed()){
								
								if(SessionManager.getHighestBidFloatForAuction(session, auction)==auction.getMinPrice()){
									itemList.add(item);
									itemStatus.put(item.getName(), "reauction");
								}else{
									itemList.add(item);
									itemStatus.put(item.getName(), "sold");
									
								}
								
							}else{
								itemList.add(item);
								itemStatus.put(item.getName(), "in_auction");
								
							}
							
							
						}
						
				
					}
					request.setAttribute("itemStatus", itemStatus);
					request.setAttribute("items", itemList);
					request.getRequestDispatcher("auction_item.ftl").forward(request, response);
					
				} catch (DTException e) {
					e.printStackTrace();
				}
				 
			 }
    }
    
    
   
}
