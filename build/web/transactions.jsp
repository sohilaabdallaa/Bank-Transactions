<%-- 
    Document   : transactions
    Created on : Dec 23, 2020, 4:42:50 PM
    Author     : Sohila
--%>
<%@page import="java.sql.*"%>
<% Class.forName("com.mysql.jdbc.Driver").newInstance(); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Transaction page</title>
    <link rel="stylesheet" type="text/css" href="newcss.css">
</head>


<%

    String url = "jdbc:mysql://localhost:3306/banksystem";
    String user = "root";

    String password = "root";
    Connection Con1 = null;

    Statement Stmt1 = null; //For Display Transactions

    ResultSet result = null;
    try {
        Con1 = DriverManager.getConnection(url, user, password);
        Stmt1 = Con1.createStatement();
        result = Stmt1.executeQuery("SELECT * FROM banktransaction" + " WHERE BTFromAccount='" + session.getAttribute("accID") + "';");
    } catch (SQLException ex) {
        // handle any errors
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }%>


<table border="1">
    <tr>
        <th>Transaction ID</th> 
        <th>Creation Date</th> 
        <th>Transaction Amount</th> 
        <th>To Account</th> 
    </tr>


    <%
        while (result.next()) {
    %>



    <tr>

        <td><%=result.getString("BankTransactionID")%></td>
        <td><%=result.getString("BTCreationDate")%></td>
        <td><%=result.getString("BTAmount")%></td>
        <td><%=result.getString("BTToAccount")%></td>

    </tr>
    <%
        } //END OF WHILE
        result.close();
        Stmt1.close();
        Con1.close();

    %>
</table> 
<br/>
<br/>
<input type="submit" value="Tranfer Money" onclick="location.href = 'transfer';" class="button"/>

<input type="submit" value="cancel transfer" onclick="location.href = 'checkdate';"class="button" />

</br>
</br>
</br>

<a href="CUSTOMERHOME.jsp" target="_parent">Back</a>
</br>
</br>
<label style="font-size: 30px;color: white">
<%        //for tansfer process
    if (session.getAttribute("message") != null) {
        out.println(session.getAttribute("message"));
        session.removeAttribute("message");
    }
%></label>
<label style="font-size: 35px;color: white">
<%
    //for canceled process
    if (session.getAttribute("message1") != null) {
        out.println(session.getAttribute("message1"));
    }
    session.removeAttribute("message1");
%>
</label>


