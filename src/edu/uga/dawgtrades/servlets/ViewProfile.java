package edu.uga.dawgtrades.servlets;
import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Allen on 11/27/14.
 */
public class ViewProfile extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession httpSession = null;
        String ssid = null;
        Session session = null;
        RegisteredUser user = null;
        httpSession = request.getSession();
        if (httpSession.getAttribute("ssid") != null) {
            ssid = (String) httpSession.getAttribute("ssid");
            session = SessionManager.getSessionById(ssid);
            if (session == null) {
                request.getRequestDispatcher("home.html").forward(request, response);
                System.out.println("No session found");
            } else {
                user = session.getUser();
                user.setName(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                user.setFirstName(request.getParameter("firstName"));
                user.setLastName(request.getParameter("lastName"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                boolean text = request.getParameter("canText")== null ? false : true ;
                user.setCanText(text);

                try {
                    session.getObjectModel().storeRegisteredUser(user);
                } catch (DTException e) {
                    e.printStackTrace();
                }
            }
            String message = "Profile has been updated";
            request.setAttribute("canText",user.getCanText());
            request.setAttribute("message",message);
            request.setAttribute("user",user);
            request.getRequestDispatcher("view_profile.ftl").forward(request,response);
        }
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        HttpSession httpSession = null;
        String ssid = null;
        Session session = null;
        RegisteredUser user = null;

        httpSession = request.getSession();
        if (httpSession.getAttribute("ssid") != null) {
            ssid = (String) httpSession.getAttribute("ssid");
            session = SessionManager.getSessionById(ssid);
            if (session == null) {
                request.getRequestDispatcher("home.html").forward(request, response);
                System.out.println("No session found");
            } else {
                user = session.getUser();
                request.setAttribute("user", user);
                boolean text = user.getCanText();
                request.setAttribute("canText",text);
                request.getRequestDispatcher("view_profile.ftl").forward(request,response);
            }
        }
    }
}

