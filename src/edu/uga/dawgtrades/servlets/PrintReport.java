package edu.uga.dawgtrades.servlets;

import javax.security.auth.login.Configuration;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


import freemarker.template.*;

import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;
/**
 * Created by Allen on 11/27/14.
 */
public class PrintReport extends javax.servlet.http.HttpServlet {
    static Connection conn = null;
    static ObjectModel objectModel = null;
    static Persistence persistence = null;
    private freemarker.template.Configuration cfg;
    // get a database connection


    public void init() {
        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        cfg = new freemarker.template.Configuration();
        // - Templates are stored in the WEB-INF/templates directory of the Web app.
        cfg.setServletContextForTemplateLoading(
                getServletContext(), "WEB-INF/templates");

    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        getConnection();
        ResultSet rs = null;
        Statement stmt = null;
        PrintWriter out = response.getWriter();
        String date = "2014-" + request.getParameter("month");


        String query ="SELECT COUNT(id) AS number FROM auction WHERE CONTAINS(Column,date);
        try {
            //stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            String numberOfAuctions = "Number of auctions: " + rs.getString("number");
            out.print(numberOfAuctions);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "select * from auction";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int i = 0;
        assert rs != null;
        try {
            while (rs.next()) {
                    String columnValue = rs.getString(i);
                    out.print(columnValue);
                i++;
                }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("");

        query ="SELECT COUNT(id) AS number FROM user";
        try {
            //stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            String numberOfUsers = "Number of users: " + rs.getString("number");
            out.print(numberOfUsers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "select * from user";
        try {
           // stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        i = 0;
        assert rs != null;
        try {
            while (rs.next()) {
                String columnValue = rs.getString(i);
                System.out.print(columnValue);
                i++;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("");

    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        // Prepare template input
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("message","testing this shit ");
        // Get the templat object
        Template template = cfg.getTemplate("print_report.ftl");

        // Prepare the HTTP response:
        // - Use the charset of template for the output
        // - Use text/html MIME-type
        response.setContentType("text/html; charset=" + template.getEncoding());
        Writer out = response.getWriter();

        // Merge the data-model and the template
        try {
            template.process(root, out);
        } catch (TemplateException e) {
            throw new ServletException(
                    "Error while processing FreeMarker template", e);
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
