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
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Sohila
 */
@WebServlet(urlPatterns = {"/checkdate"})
public class checkdate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        //for get Current Day,Year,Month
        Date Currentdate = new Date();
        Calendar currentCalender = Calendar.getInstance();
        currentCalender.setTime(Currentdate);
        int CurrentMonth = currentCalender.get(Calendar.MONTH) + 1;
        int CurrentDay = currentCalender.get(Calendar.DAY_OF_MONTH);
        int CurrentYear = currentCalender.get(Calendar.YEAR);

        //for Using it in getDataBaseDate Section Below
        Date DBdate = new Date();
        Calendar DBtransdate = Calendar.getInstance();

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println(
                    "         <link rel=\"stylesheet\" type=\"text/css\" href=\"newcss.css\">\n"
                    + "    </head>    <form \"\">\n"
                    + "        <table border=\"1\">\n"
                    + "            <tr> \n"
                    + "                <td>Transaction ID :</td>\n"
                    + "                <td><input type=\"text\" name=\"transID\"  /></td>  \n"
                    + "            </tr>\n"
                    + "        </table>    \n"
                    + "        </br>\n"
                    + "        <input type=\"submit\" value=\"Cancle Now \" onclick=\"location.href = 'checkdate';\" class=\"button\"/>\n"
                    + "</br>"
                    + "</br>"
                    + "        <a href=\"CUSTOMERHOME.jsp\">BACK</a>\n"
                    + "    </form>");

            String transID = request.getParameter("transID");

            try {

                String url = "jdbc:mysql://localhost:3306/banksystem";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                ResultSet result = null;

                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                result = Stmt.executeQuery("SELECT * FROM banktransaction WHERE BankTransactionID=" + transID + ";");
                while (result.next()) {
                    //get Date Of 
                    //Separate Day , Month
                    DBdate = result.getDate("BTCreationDate");
                    DBtransdate.setTime(DBdate);
                    int DBTMonth = DBtransdate.get(Calendar.MONTH) + 1; //+1 because months in Calendar are zero-indexed
                    int DBTDay = DBtransdate.get(Calendar.DAY_OF_MONTH);
                    int DBTYear = DBtransdate.get(Calendar.YEAR);
                    //Get Difference in Days
                    int DiffDays = CurrentDay - DBTDay;
                    Double Amount = result.getDouble("BTAmount");
                    String FromID = result.getString("BTFromAccount");
                    String ReciverAcc = result.getString("BTToAccount");
                    String tranID = result.getString("BankTransactionID");

//                    For Checking Only
//                    out.println("Current Time is ");
//                    out.println(CurrentYear);
//                    out.println(CurrentMonth);
//                    out.println(CurrentDay);
//                    out.println("DataBase Trans Time");
//                    out.println(DBTYear);
//                    out.println(DBTMonth);
//                    out.println(DBTDay);
//                    out.println("Difference In days Is: ");
//                    out.println(DiffDays);
                    if (DBTYear == CurrentYear && DBTMonth == CurrentMonth && (DiffDays == 1 || DiffDays == 0)) { //Date of Transation occurs before CurrentDate && difference is 0 OR 1

                        Stmt.executeUpdate("delete from banktransaction where BankTransactionID=" + tranID + ";");
                        Stmt.executeUpdate("update bank_account set BACurrentBalance=(BACurrentBalance+'" + Amount + "') where BankAcountID = '" + FromID + "';");
                        Stmt.executeUpdate("update bank_account set BACurrentBalance=(BACurrentBalance-'" + Amount + "') where BankAcountID = " + ReciverAcc + ";");
                        String message1 = " transaction is Canceled successfully ";
                        request.getSession().setAttribute("message1", message1);
                        response.sendRedirect("transactions.jsp");

                    } else {
                        response.sendRedirect("transactions.jsp");

                    }
                    result.close();
                    Stmt.close();
                    Con.close();
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(checkdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(checkdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
