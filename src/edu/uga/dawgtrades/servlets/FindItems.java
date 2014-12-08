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
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

/**
 * Created by Allen on 11/27/14.
 */
public class FindItems extends javax.servlet.http.HttpServlet {
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
			 String auctionId = request.getParameter("auction_id");
			 int auction_id = Integer.parseInt(auctionId);
			 Auction auctionModel = session.getObjectModel().createAuction();
			 auctionModel.setId(auction_id);
			 Auction auction = null;
			 try {
				Iterator<Auction> auctions = session.getObjectModel().findAuction(auctionModel);
				int count=0;
				while(auctions.hasNext()){
					
					auction = auctions.next();
					count++;
					
				}
				System.out.println(count);
				Item item = session.getObjectModel().getItem(auction);
				if(item.getOwnerId()==session.getUser().getId()){
					
					request.setAttribute("owned",true);
					
				}else{
					
					request.setAttribute("owned",false);

				}
				request.setAttribute("item", item);
				Date expiration = auction.getExpiration();
				String time = expiration.toString();
				request.setAttribute("expiration", time);
				request.setAttribute("auction", auction);
				Bid currentBid = SessionManager.getHighestBidForAuction(session, auction);
				
				if(!currentBid.isPersistent()){
				
					request.setAttribute("highestBidder", false);
					request.setAttribute("currentBid", auction.getMinPrice());
					
				}else{
					if(currentBid.getRegisteredUser().getId()==session.getUser().getId()){
						
						request.setAttribute("highestBidder", true);
					
						}else{
					
							request.setAttribute("highestBidder", false);

					
						}
					
					request.setAttribute("currentBid", currentBid.getAmount());
				}
				System.out.println(auction.getExpiration());
				request.getRequestDispatcher("viewItem.ftl").forward(request, response);
				
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
				 
				Iterator<Auction> auctions = null;
				if(request.getParameter("category")==null){
						System.out.println("Category is null");
				       auctions = session.getObjectModel().findAuction(null);
				 }else{
					  int categoryId = Integer.parseInt(request.getParameter("category"));
					  System.out.println(categoryId);
					  if(categoryId==-1){
						 auctions = session.getObjectModel().findAuction(null);
					  }else{
						  
						  auctions = session.getObjectModel().findAuction(null);

						  
						  
					  }
					 
				 }
				List<Item> itemList = new ArrayList<Item>();
				while(auctions.hasNext()){
			//		System.out.println("Item found");
			//		auctionList.add(auctions.next());
					Auction auctionComp = auctions.next();
						if(auctionComp.getIsClosed()==false){
						Item item = session.getObjectModel().getItem(auctionComp);
					
						item.setId(auctionComp.getId());
						itemList.add(item);
						System.out.println("Item found");
						}
				}
				System.out.println(itemList.size());
				request.setAttribute("items", itemList);
				request.getRequestDispatcher("findItems.ftl").forward(request, response);
				
			} catch (DTException e) {

				e.printStackTrace();
			}
			 
		 }
    	
    		
    }
    
   
}
