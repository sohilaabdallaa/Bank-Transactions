/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Sohila
 */
@WebServlet(urlPatterns = {"/validate"})
public class validate extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            PrintWriter out = response.getWriter();  
        
             String logpassword=request.getParameter("password");
            String logid=request.getParameter("customerid");
           
        try {
            
           
            
             Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/banksystem";
            Connection con=null;
            Statement stm=null;
            ResultSet result=null;
            
            
            con=DriverManager.getConnection(url,"root","root");
            stm=con.createStatement();
            result=stm.executeQuery("SELECT * FROM customer"+" WHERE CustomerID='"+logid+"'AND CustomerPassword='"+logpassword+"';");
            
            while(result.next())
            {
            String DBpassword=result.getString("CustomerPassword");
            String DBid=result.getString("CustomerID");   
            
            
                if (DBid.equals(logid)
                       && DBpassword.equals(logpassword)) {
                    HttpSession session1 = request.getSession(true);
                    session1.setAttribute("CustomerID", logid);
                    response.sendRedirect("CUSTOMERHOME.jsp");
                } 
                    
                con.close();
            }
                response.sendRedirect("index.html");
                
        } catch (SQLException ex) {
            Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}

  