package edu.uga.dawgtrades.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Auction;
import edu.uga.dawgtrades.model.Bid;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

/**
 * Created by Allen on 11/27/14.
 */
public class BidOnItem extends javax.servlet.http.HttpServlet {
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
			 if(request.getParameter("auction_id")!=null && request.getParameter("bid")!=null){
				 
				 String auctionId = request.getParameter("auction_id");
					int auction_id = Integer.parseInt(auctionId);
					Auction modelAuction = session.getObjectModel().createAuction();
					modelAuction.setId(auction_id);
					try{
					Iterator<Auction> auctions = session.getObjectModel().findAuction(modelAuction);
					int count = 0;
					Auction auction = null;
					while(auctions.hasNext()){
						count++;
						auction = auctions.next();
					}
					System.out.println(count+" auctions detected");
					float bid = -100;
					try{
					
					 bid = Float.parseFloat(request.getParameter("bid"));
					
					
					}catch(NumberFormatException e){
						System.out.println("Improper input");
						Item item = session.getObjectModel().getItem(auction);
						request.setAttribute("item", item);
						request.setAttribute("auction", auction);
						
						request.setAttribute("message", "Sorry, but that was not a proper input.");
						System.out.println("Attributes set");
						request.setAttribute("currentBid", SessionManager.getHighestBidForAuction(session, auction));
						request.getRequestDispatcher("bid_on_item.ftl").forward(request, response);
						return;
						
					}
					float highestBid = SessionManager.getHighestBidFloatForAuction(session, auction);
					
					if(highestBid==-1){
			
							highestBid = auction.getMinPrice();
					}
						if(bid<highestBid){
							System.out.println("Bid did not meet minimum");
							Item item = session.getObjectModel().getItem(auction);
							request.setAttribute("item", item);
							request.setAttribute("auction", auction);
							
							request.setAttribute("message", "Sorry, but that was not a valid bid.");
							System.out.println("Attributes set");
							request.setAttribute("currentBid", SessionManager.getHighestBidForAuction(session, auction));
							request.getRequestDispatcher("bid_on_item.ftl").forward(request, response);
						
					}else{
						
						Bid newBid =session.getObjectModel().createBid(auction, session.getUser(), bid);
						session.getObjectModel().storeBid(newBid);
						request.getRequestDispatcher("bidPlace.ftl").forward(request, response);;
						
					}
					
					
					}catch(DTException e){
						e.printStackTrace();
					}
				 
			 }else{
				 
				 System.out.println("No attribute detected");
		    	 request.getRequestDispatcher("login").forward(request, response);
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
				if(request.getParameter("auction_id")!=null){
						
						if(request.getParameter("error_in_bid")!=null){
							
							request.setAttribute("message", "There was an error in placing your bid. Please ensure you're entering a valid numeric bid higher than the current bid.");
							
						}else{
							
							request.setAttribute("message", "Enter bid below.");
						}
					
						String auctionId = request.getParameter("auction_id");
						int auction_id = Integer.parseInt(auctionId);
						Auction modelAuction = session.getObjectModel().createAuction();
						modelAuction.setId(auction_id);
						try{
						Iterator<Auction> auctions = session.getObjectModel().findAuction(modelAuction);
						int count = 0;
						Auction auction = null;
						while(auctions.hasNext()){
							count++;
							auction = auctions.next();
						}
						System.out.println(count+"number of auctions detected");
						Item item = session.getObjectModel().getItem(auction);
						
						request.setAttribute("item", item);
						request.setAttribute("auction", auction);
						request.setAttribute("currentBid", SessionManager.getHighestBidFloatForAuction(session, auction));
						System.out.println("Attributes set");
						request.getRequestDispatcher("bid_on_item.ftl").forward(request, response);
						
						}catch(DTException e){
							e.printStackTrace();
						}
    	
    	
    			
    	}else{
    		System.out.println("No attribute detected");
    		request.getRequestDispatcher("login").forward(request, response);
    	}
    }
		 
		
		 
    }
   
}