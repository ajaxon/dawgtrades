package edu.uga.dawgtrades.servlets;
import edu.uga.dawgtrades.model.DTException;
import edu.uga.dawgtrades.model.ObjectModel;
import edu.uga.dawgtrades.model.RegisteredUser;
import edu.uga.dawgtrades.model.impl.ObjectModelImpl;
import edu.uga.dawgtrades.persist.Persistence;
import edu.uga.dawgtrades.persist.impl.DbUtils;
import edu.uga.dawgtrades.persist.impl.PersistenceImpl;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import static edu.uga.dawgtrades.authentication.EmailSender.sendEmail;
/**
 * Created by Allen on 11/27/14.
 */
public class ResetPassword extends javax.servlet.http.HttpServlet {
    static Connection conn = null;
    static ObjectModel objectModel = null;
    static Persistence persistence = null;
    // get a database connection
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String email = (String)request.getParameter("email");
        System.out.println(email);
        getConnection();
        System.out.println("Connection Established");
        RegisteredUser modelUser = objectModel.createRegisteredUser();
        modelUser.setEmail(email);
        Iterator<RegisteredUser> users = null;
        try {
            users = objectModel.findRegisteredUser(modelUser);
        } catch (DTException e1) {
            e1.printStackTrace();
        }
        RegisteredUser user = null;
        while(users.hasNext()){
            user = users.next();
            System.out.println("User returned");
        }
        try {
            this.disconnect();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        if(user!=null){
            String oldPass = user.getPassword();
            try {
                sendEmail(email, "Forgotten password from your pals at Dawgtrades", "Here's your forgotten password:\n" + oldPass);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            request.setAttribute("message", "Your password has been sent! Check your email.");
            request.getRequestDispatcher("reset_password.ftl").forward(request,response);
// }
        }else{
            System.out.println("No user was found");
            request.setAttribute("message", "Sorry but that is not a valid email in our system.");
            request.getRequestDispatcher("reset_password.ftl").forward(request,response);
        }
    }
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setAttribute("message", "Enter your email and you will receive a timely email");
        request.getRequestDispatcher("reset_password.ftl").forward(request,response);
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