package edu.uga.dawgtrades.servlets;
import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.persist.Persistence;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Allen on 11/27/14.
 */
public class ViewProfile extends javax.servlet.http.HttpServlet {

    static Connection conn = null;
    static Persistence persistence = null;

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
                String text = request.getParameter("canText");
                if(text == "false"){
                    user.setCanText(false);
                }else{
                    user.setCanText(true);
                }
//                try {
//                    persistence.saveRegisteredUser(user);
//                } catch (DTException e) {
//                    e.printStackTrace();
//                }
            }
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
                if(user.getCanText() == false){
                    request.setAttribute("canText", false);
                }else{
                    request.setAttribute("canText", true);
                }
                request.getRequestDispatcher("view_profile.ftl").forward(request,response);
            }
        }
    }
}


