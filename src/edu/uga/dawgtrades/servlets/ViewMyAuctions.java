package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Allen on 11/27/14.
 */
public class ViewMyAuctions extends javax.servlet.http.HttpServlet {
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
                request.setAttribute("item", item);
                Date expiration = auction.getExpiration();
                String time = expiration.toString();
                request.setAttribute("expiration", time);
                request.setAttribute("auction", auction);
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

                RegisteredUser user = null;
                user = session.getUser();

                Item item = session.getObjectModel().createItem();
                item.setOwnerId(user.getId());

                Iterator<Item> items = session.getObjectModel().findItem(item);

                List<Item> itemList = new ArrayList<Item>();
                while(items.hasNext()){
                    //		System.out.println("Item found");
                    //		auctionList.add(auctions.next());
                    Item itemComp = items.next();
                    Auction auction = session.getObjectModel().getAuction(itemComp);

                    itemComp.setId(auction.getId());
                    itemList.add(item);
                    System.out.println("Item found");
                }
                System.out.println(itemList.size());
                request.setAttribute("items", itemList);
                request.getRequestDispatcher("view_my_auctions.ftl").forward(request, response);

            }

            catch (DTException e) {

                e.printStackTrace();
            }

        }


    }
}
