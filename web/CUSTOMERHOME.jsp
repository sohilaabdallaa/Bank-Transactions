<%-- 
    Document   : CUSTOMERHOME
    Created on : Dec 22, 2020, 1:10:42 PM
    Author     : Sohila
--%>
<%@page import="java.sql.*"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Home Page</title>
         <link rel="stylesheet" type="text/css" href="newcss.css">
    </head>    
    <body>

        <%
            HttpSession s = request.getSession();
            String customerID = s.getAttribute("CustomerID").toString();

            String url = "jdbc:mysql://localhost:3306/banksystem";
            String user = "root";

            String password = "root";
            Connection Con = null;
            Statement Stmt = null;
            ResultSet result = null;
            try {
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                result = Stmt.executeQuery("SELECT * FROM bank_account" + " WHERE CustomerID='" + customerID + "';");

            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
            //get the customer id which is in result and then check if it null of not
        %> 

        <%
            if (result.next()) {%>

         <label style="font-size: 30px;">ID :  <%=result.getString("CustomerID")%>  </label>
        <br/>        
        <label style="font-size: 30px;">Current Balance :  <%=result.getString("BACurrentBalance")%></label>
        <br/>
        <label style="font-size: 30px;">Bank Account ID :  <%=result.getString("BankAcountID")%></label>
        <br/>

        <%
            String accID = result.getString("BankAcountID");
            session.setAttribute("accID", accID);
            result.close();
            Stmt.close();
%>
        <br/>
        <input type="submit" value="View Transactions List" onclick="location.href = 'transactions.jsp';"class="button" style="font-size: 15px"/>
        <%
       
        } else { 
        %>
        <input type="submit" value="addacount" name="ADD Account" onclick="location.href = 'addaccount';" class="button"/>


        <%}

        %>
        
        <%Con.close();%>
        <input type="submit" value="Log Out" class="button" onclick="location.href = 'index.html';"/>
    </body>
</html>
