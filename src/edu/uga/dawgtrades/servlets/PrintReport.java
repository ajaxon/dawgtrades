package edu.uga.dawgtrades.servlets;


import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allen on 11/27/14.
 */
public class PrintReport extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException, IOException {

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
        }else {
            RegisteredUser user = session.getUser();
            if (user.getIsAdmin() == true) {

                try {

                    int userNumber = 0;
                    float userRevenue = 0.0f;
                    int completedAuctions = 0;
                    float auctionsValue = 0.0f;
                    int openAuctions = 0;
                    float membershipPrice = 0.0f;
                    int adminNumber = 0;

                    Iterator<Auction> auctionIterator = session.getObjectModel().findAuction(null);
                    Iterator<RegisteredUser> userIterator = session.getObjectModel().findRegisteredUser(null);
                    Membership membership = session.getObjectModel().findMembership();

                    if(membership != null){
                        membershipPrice = membership.getPrice();
                    }

                    while(auctionIterator.hasNext()){
                        Auction tmpauction = auctionIterator.next();
                        if(tmpauction.getIsClosed()){
                            completedAuctions++;
                            auctionsValue = auctionsValue + tmpauction.getSellingPrice();
                        }
                        else openAuctions++;
                    }

                    while(userIterator.hasNext()){
                        RegisteredUser tmpUser = userIterator.next();
                        if(tmpUser.getIsAdmin()) adminNumber++;
                        else {
                            userNumber++;
                            userRevenue = userRevenue + membershipPrice;
                        }
                    }

                    request.setAttribute("users", userNumber);
                    request.setAttribute("revenue", userRevenue);
                    request.setAttribute("comauctions", completedAuctions);
                    request.setAttribute("auctionsvalue", auctionsValue);
                    request.setAttribute("ongauctions", openAuctions);
                    request.setAttribute("admins", adminNumber);

                    request.getRequestDispatcher("print_report.ftl").forward(request, response);


                } catch (DTException e) {

                    e.printStackTrace();
                }

            }
        }

    }
}
