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
import java.time.LocalDateTime;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/transfer"})
public class transfer extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("  <link rel=\"stylesheet\" type=\"text/css\" href=\"newcss.css\">\n"
                    + "    </head>"
                    + "<form \"\">\n"
                    + "        <table border=\"1\">\n"
                    + "            <tr> \n"
                    + "                <td> Transfered Amount:</td>\n"
                    + "                <td><input type=\"text\" name=\"amount\"  /></td>  \n"
                    + "            </tr>\n"
                    + "            <tr> \n"
                    + "                <td>Transfer To Account ID:</td>\n"
                    + "                <td><input type=\"text\" name=\"toaccount\"  /></td>  \n"
                    + "            </tr>\n"
                    + "\n"
                    + "        </table>    \n"
                    + "        </br>\n"
                    + "        <input type=\"submit\" value=\"Transfer\" onclick=\"location.href = 'transfer';\" class=\"button\"/>\n"
                    + "</br>"
                    + "</br>"
                    + "        <a href=\"CUSTOMERHOME.jsp\">BACK</a>\n"
                    + "    </form>");

            LocalDateTime d = LocalDateTime.now(); //For insert transaction Date
            //Get Recevier Account ID
            //Get Amount to be Transfered
            String Amount = request.getParameter("amount");
            String ReciverAcc = request.getParameter("toaccount");
            //Get sender Account ID
            HttpSession s = request.getSession();
            String FromID = s.getAttribute("accID").toString();

            //connection To DataBase
            try {

                String url = "jdbc:mysql://localhost:3306/banksystem";
                String user = "root";
                String password = "root";
                Connection Con2 = null;
                Statement Stmt2 = null; //For Display Transactions

                Con2 = DriverManager.getConnection(url, user, password);
                Stmt2 = Con2.createStatement();
                Stmt2.executeUpdate("update bank_account set BACurrentBalance=(BACurrentBalance-'" + Amount + "') where BankAcountID = '" + FromID + "';");
                Stmt2.executeUpdate("update bank_account set BACurrentBalance=(BACurrentBalance+'" + Amount + "') where BankAcountID = " + ReciverAcc + ";");
                String line = "INSERT INTO banktransaction(BTCreationDate,BTAmount,BTToAccount,BTFromAccount)"
                        + "VALUES("
                        + "'" + d + "',"
                        + "'" + Amount + "',"
                        + "'" + ReciverAcc + "',"
                        + "'" + FromID + "')";
                int done = Stmt2.executeUpdate(line);
                Stmt2.close();
                Con2.close();
                if (done > 0) {
                    //For Display Message After Trasncation Done
                    String message = " transaction is done successfully ";
                    request.getSession().setAttribute("message", message);
                    response.sendRedirect("transactions.jsp");
                } else {
                    out.println("Transfer Is Failed");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
