package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Allen on 11/27/14.
 */
public class DeleteUser extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        HttpSession httpSession = null;
        String ssid = null;
        Session session = null;

        httpSession = request.getSession();

        if (httpSession.getAttribute("ssid") != null) {
            ssid = (String) httpSession.getAttribute("ssid");
        } else {
            System.out.println("No ssid found");
            request.getRequestDispatcher("home.html").forward(request, response);
        }

        session = SessionManager.getSessionById(ssid);
        if (session == null) {
            request.getRequestDispatcher("home.html").forward(request, response);
            System.out.println("No session found");
        } else {
            String user_id = request.getParameter("userID");
            int userId = Integer.parseInt(user_id);
            RegisteredUser user = session.getObjectModel().createRegisteredUser();
            user.setId(userId);
            try{
                Iterator<RegisteredUser> users = session.getObjectModel().findRegisteredUser(user);
                RegisteredUser deleteUser = null;
                while(users.hasNext()){
                    deleteUser = users.next();
                }
                session.getObjectModel().deleteRegisteredUser(deleteUser);
                request.getRequestDispatcher("delete_user.ftl").forward(request,response);
            }catch(DTException e){
                e.printStackTrace();
            }
        }
    }
}
