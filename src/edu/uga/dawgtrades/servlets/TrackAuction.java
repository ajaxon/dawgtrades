package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by Allen on 11/27/14.
 */
public class TrackAuction extends javax.servlet.http.HttpServlet {
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



            try {
                Item item = null;
                Bid bid = null;
                Auction auction = null;

                HashMap<Auction,Map<String,Object>> auctions= new HashMap<Auction, Map<String, Object>>();
                RegisteredUser user = session.getUser();
                Bid modelBid = session.getObjectModel().createBid();
                modelBid.setUser(user);
                Iterator<Bid> bidIterator = session.getObjectModel().findBid(modelBid);
                while(bidIterator.hasNext()){
                    bid = bidIterator.next();

                    Auction auctionModel = session.getObjectModel().createAuction();
                    auctionModel.setId(bid.getAuction().getId());
                    Iterator<Auction> auctionsIter = session.getObjectModel().findAuction(auctionModel);
                    while(auctionsIter.hasNext()){
                        auction = auctionsIter.next();
                        item = session.getObjectModel().getItem(auction);
                        auctions.put(auction,new HashMap<String,Object>());
                        if(auctions.get(auction).get("bid")==null){
                            auctions.get(auction).put("bid",bid);
                        }else{
                            Bid bidTemp = (Bid) auctions.get(auction).get("bid");
                            if(bidTemp.getAmount() < bid.getAmount()){
                                auctions.get(auction).put("bid",bid);
                            }
                        }

                        auctions.get(auction).put("item",item);
                    }





                }






                request.getRequestDispatcher("findItems.ftl").forward(request, response);

            } catch (DTException e) {

                e.printStackTrace();
            }
        }

    }
}
