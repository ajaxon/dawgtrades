package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.Category;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.Item;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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

                // need to allow guest user
                //session = new Session();
            } else {
                try {
                    if(request.getParameter("categoryID")==null) {
                        HashMap<String,ArrayList<Category>> children = new HashMap<String, ArrayList<Category> >();
                        Category category = null;
                        Category modelCategory = session.getObjectModel().createCategory();
                        modelCategory.setParentId(0);
                        Iterator<Category> categoriesIter = session.getObjectModel().findCategory(modelCategory);
                        List<Category> categories = new ArrayList<Category>();
                        while (categoriesIter.hasNext()) {
                            category = categoriesIter.next();
                            categories.add(category);
                            Iterator<Category> childs = session.getObjectModel().getChild(category);
                            ArrayList<Category> kids = new ArrayList<Category>();
                            while(childs.hasNext()){
                                kids.add(childs.next());
                            }
                            children.put(category.getName(),kids);
                        }
                        request.setAttribute("user", session.getUser());
                        request.setAttribute("categories", categories);
                        request.setAttribute("children",children);
                        request.getRequestDispatcher("browse_category.ftl").forward(request, response);
                    }
                    else
                    {
                        Category category = null;
                        List<Category> children = new LinkedList<Category>();

                        Category modelCategory = session.getObjectModel().createCategory();
                        modelCategory.setId(Integer.parseInt(request.getParameter("categoryID")));
                        Iterator<Category> cats = session.getObjectModel().findCategory(modelCategory);
                        while(cats.hasNext()){
                            category = cats.next();
                        }
                        Iterator<Category> childrenIter = session.getObjectModel().getChild(category);
                        while(childrenIter.hasNext()){
                            children.add(childrenIter.next());
                        }


                        request.setAttribute("category",category);
                        request.setAttribute("children",children);
                        request.getRequestDispatcher("browse_category.ftl").forward(request,response);
                    }

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }


        }
    }
}
