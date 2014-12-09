package edu.uga.dawgtrades.servlets;

import edu.uga.dawgtrades.authentication.Session;
import edu.uga.dawgtrades.authentication.SessionManager;
import edu.uga.dawgtrades.model.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allen on 11/27/14.
 */
public class UpdateCategory extends javax.servlet.http.HttpServlet {
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
                    Category category =  null;
                    List<AttributeType> attributeTypes = new LinkedList< AttributeType>();
                    Category categoryModel = session.getObjectModel().createCategory();
                    categoryModel.setId(Integer.parseInt(request.getParameter("id")));

                    Iterator<Category> categoryIterator = session.getObjectModel().findCategory(categoryModel);
                    while(categoryIterator.hasNext()){
                        category = categoryIterator.next();
                    }

                    // now have the category to be updated
                    category.setName(request.getParameter("name"));
                    category.setParentId(Integer.parseInt(request.getParameter("parent_id")));
                    session.getObjectModel().storeCategory(category);
                    ///




                    AttributeType attributeType = null;
                    Iterator<AttributeType> attributeTypeIterator = session.getObjectModel().getAttributeType(category);
                    int i =1;
                    while(attributeTypeIterator.hasNext()){
                        attributeType = attributeTypeIterator.next();
                        attributeType.setName(request.getParameter("attr_name"+i));
                        session.getObjectModel().storeAttributeType(attributeType);
                        i++;
                    }

                    while(request.getParameter("attr_name"+i) != null){
                        AttributeType attrType = session.getObjectModel().createAttributeType(category,request.getParameter("attr_name"+i));
                        session.getObjectModel().storeAttributeType(attrType);
                        i++;

                    }
                    String message = "Category"+category.getName()+" updated";
                    request.setAttribute("message",message);
                    request.getRequestDispatcher("home.html").forward(request, response);

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }else
                request.setAttribute("message","Only admin can update a category");
                request.getRequestDispatcher("home.html").forward(request,response);
        }




    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

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
                    List<AttributeType> attributeTypes = new LinkedList< AttributeType>();
                    List<Category> categories = new LinkedList<Category>();
                    Category category = null;

                    Iterator<Category> categoryIterator = session.getObjectModel().findCategory(null);
                    while(categoryIterator.hasNext()){
                        categories.add(categoryIterator.next());
                    }
                    Category modelCategory = session.getObjectModel().createCategory();
                   modelCategory.setId(Integer.parseInt(request.getParameter("categoryID")));

                    Iterator<Category> categoriesIter = session.getObjectModel().findCategory(modelCategory);
                    while(categoriesIter.hasNext()){
                        category = categoriesIter.next();
                    }
                    // Remove current parent category
                    Category parent = session.getObjectModel().getParent(category);
                    long parent_id = 0;
                    String parent_name=null;
                    if(parent != null){
                        categories.remove(parent);
                        parent_id = category.getParentId();
                        parent_name = parent.getName();

                    }else
                      parent_name = "None";



                    Iterator<AttributeType> attributeTypeIterator = session.getObjectModel().getAttributeType(category);
                    while(attributeTypeIterator.hasNext()){
                        attributeTypes.add(attributeTypeIterator.next());
                    }
                    request.setAttribute("category", category);
                    request.setAttribute("parent_id",parent_id);
                    request.setAttribute("parent_name",parent_name);
                    request.setAttribute("categories",categories);
                    request.setAttribute("attribute_types", attributeTypes);
                    request.getRequestDispatcher("update_category.ftl").forward(request, response);

                } catch (DTException e) {

                    e.printStackTrace();
                }

            }else
                request.getRequestDispatcher("home.html").forward(request,response);
        }

    }
}
