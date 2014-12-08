package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.RegisteredUser;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Allen on 11/27/14.
 */
public class ViewProfile extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

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
                String name = user.getName();
                request.setAttribute("name", name);
                String pass = user.getPassword();
                request.setAttribute("pass", pass);
                String fn = user.getFirstName();
                request.setAttribute("fn", fn);
                String ln = user.getLastName();
                request.setAttribute("ln", ln);
                String email = user.getEmail();
                request.setAttribute("email", email);
                String phone = user.getPhone();
                request.setAttribute("phone", phone);
                Boolean text = user.getCanText();
                request.setAttribute("text", text);
            }
        }
    }
}


