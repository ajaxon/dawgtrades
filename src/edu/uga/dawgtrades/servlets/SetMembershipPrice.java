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
public class SetMembershipPrice extends javax.servlet.http.HttpServlet {
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
        }else {
            RegisteredUser user = session.getUser();
            if (user.getIsAdmin() == true) {

                try {

                    float price = Float.parseFloat(request.getParameter("price"));

                    Membership membership = session.getObjectModel().findMembership();
                    if(membership == null){
                        Membership newMembership = session.getObjectModel().createMembership(price,new Date());
                        session.getObjectModel().storeMembership(newMembership);
                    }else {

                        membership.setPrice(price);
                        session.getObjectModel().storeMembership(membership);
                    }
                    String message = "Membership price set";
                    request.setAttribute("message",message);
                    request.setAttribute("user",user);
                    request.getRequestDispatcher("index.ftl").forward(request,response);
                }
                catch (DTException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {

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
                    Membership membership = session.getObjectModel().findMembership();

                    request.setAttribute("membership", membership);

                    request.getRequestDispatcher("membership.ftl").forward(request, response);

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }
        }

    }
}
