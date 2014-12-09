package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.RegisteredUser;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static edu.uga.dawgtrades.authentication.EmailSender.sendEmail;

/**
 * Created by Allen on 11/27/14.
 */
public class ResetPassword extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        HttpSession httpSession = null;
        String ssid = null;
        Session session = null;
        String actualEmail = null;
        String email = null;
        RegisteredUser user = null;


        httpSession = request.getSession();
        if (httpSession.getAttribute("ssid") != null) {
            ssid = (String) httpSession.getAttribute("ssid");
            session = SessionManager.getSessionById(ssid);
            if(session == null){
                request.getRequestDispatcher("home.html").forward(request,response);
            }else{
                user = session.getUser();
                String oldPass = user.getPassword();
//                actualEmail = user.getEmail();
                email = (String)request.getParameter("email");
            //    if(email == null || actualEmail != email){
//                    String message = "Please enter a real email.";
//                    request.setAttribute("message", message);
            //    }else{
                    try {
                        sendEmail(email, "Forgotten password from your pals at Dawgtrades", "Here's your forgotten password:\n" + oldPass);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    request.getRequestDispatcher("reset_password.ftl").forward(request,response);
            //    }
            }
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
            if(session == null){
                request.getRequestDispatcher("home.html").forward(request,response);
            }else{
                request.getRequestDispatcher("reset_password.ftl").forward(request,response);
            }
        }
    }

}