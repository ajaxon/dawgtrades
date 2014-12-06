package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Allen on 11/27/14.
 */
public class BrowseCategory extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        HttpSession httpSession = null;
        String ssid = null;
        Session session = null;


        httpSession = request.getSession();

        if (httpSession.getAttribute("ssid") != null) {

            ssid = (String) httpSession.getAttribute("ssid");


            session = SessionManager.getSessionById(ssid);
            if (session == null) {

                request.getRequestDispatcher("home.html").forward(request, response);
                System.out.println("No session found");
            } else {
                try {
                    Iterator<Category> categoriesIter = session.getObjectModel().findCategory(null);
                    List<Category> categories = new ArrayList<Category>();
                    while (categoriesIter.hasNext()) {
                        categories.add(categoriesIter.next());
                    }
                    request.setAttribute("user",session.getUser());
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("browse_category.ftl").forward(request, response);

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }


        }
    }
}
