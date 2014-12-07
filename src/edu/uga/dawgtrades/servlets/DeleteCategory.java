package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.AttributeType;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.RegisteredUser;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allen on 11/27/14.
 */
public class DeleteCategory extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        // get paramater categoryID

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
                    Category category = null;
                    Category modelCategory = session.getObjectModel().createCategory();
                    modelCategory.setId(Integer.parseInt(request.getParameter("categoryID")));
                    Iterator<Category> categoryIterator = session.getObjectModel().findCategory(modelCategory);
                    while(categoryIterator.hasNext()){
                        category = categoryIterator.next();
                    }
                    session.getObjectModel().deleteCategory(category);



                    request.getRequestDispatcher("home.html").forward(request, response);

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }else
                request.getRequestDispatcher("home.html").forward(request,response);
        }












    }
}
