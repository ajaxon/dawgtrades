package edu.uga.dawgtrades.servlets;
import edu.uga.dawgtrades.model.*;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allen on 12/4/14.
 */
public class DefineCategory extends HttpServlet {

    static Connection conn = null;
    static ObjectModel objectModel = null;
    static Persistence persistence = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getConnection();
        int parentID;

        String name = request.getParameter("name");
        parentID = Integer.parseInt(request.getParameter("parent_id"));

        Category category = objectModel.createCategory();
        category.setParentId(parentID);
        category.setName(name);


        try {
            persistence.saveCategory(category);
        } catch (DTException e) {
            e.printStackTrace();
        }



        String attr1 = request.getParameter("attr_name1");
        String attr2 = request.getParameter("attr_name2");

        if(attr1 != null) {
            try {
                AttributeType attributeType1 = objectModel.createAttributeType(category, attr1);
                objectModel.storeAttributeType(attributeType1);
            } catch (DTException e) {
                e.printStackTrace();
            }
        }
        if(attr2 != null){
            AttributeType attributeType2 = null;
            try {
                attributeType2 = objectModel.createAttributeType(category,attr2);
                objectModel.storeAttributeType(attributeType2);
            } catch (DTException e) {
                e.printStackTrace();
            }

        }
        try {
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("home.html").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        getConnection();
        Iterator<Category> categoriesIter = null;
        Category category = null;
        List<Category> categories = new LinkedList<Category>();
        try {
            categoriesIter = objectModel.findCategory(null);
        } catch (DTException e) {
            e.printStackTrace();
        }

        while(categoriesIter.hasNext()){
            category = categoriesIter.next();
            categories.add(category);
        }
        request.setAttribute("categories",categories);
        request.getRequestDispatcher("define_category.ftl").forward(request, response);



        try {
            disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getConnection()
    {
        try {
            conn = DbUtils.connect();

        }
        catch (Exception seq) {
            System.err.println( "Register.java getConnection: Unable to obtain a database connection" );
        }

        // obtain a reference to the ObjectModel module
        objectModel = new ObjectModelImpl();

        // obtain a reference to Persistence module and connect it to the ObjectModel
        persistence = new PersistenceImpl( conn, objectModel );

        // connect the ObjectModel module to the Persistence module
        objectModel.setPersistence( persistence );
    }
    public void disconnect() throws SQLException {
        conn.close();
    }

}
