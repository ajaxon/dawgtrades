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
                Set<Item> items = new LinkedHashSet<Item>();
                HashMap<Integer,Float> auctions = new HashMap<Integer, Float>();
                //HashMap<Auction,Map<String,Object>> auctions= new HashMap<Auction, Map<String, Object>>();
                //List<Auction> auctionList = new LinkedList<Auction>();
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

                        if(auctions.get(auction.getId())==null){
                            auctions.put( (int) auction.getId(),bid.getAmount());
                        }else{
                           float value = auctions.get(auction.getId());
                            if(value < bid.getAmount()){
                                auctions.put((int)auction.getId(), bid.getAmount());
                            }
                        }

                            item = session.getObjectModel().getItem(auction);
                            items.add(item);



                    }





                }
                List<Item> list = new ArrayList<Item>(items);

                request.setAttribute("items", list);
                request.setAttribute("auctions",auctions);



                request.getRequestDispatcher("track_auctions.ftl").forward(request, response);

            } catch (DTException e) {

                e.printStackTrace();
            }
        }

    }
}
