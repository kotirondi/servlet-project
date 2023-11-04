import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CloseAccount extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            
            String AccountNumber = request.getParameter("accno");
            String Pin = request.getParameter("pin");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/banking";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            String qry = "DELETE FROM Account WHERE AccountNo=? AND Password=?";
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.setString(1, AccountNumber);
            stmt.setString(2, Pin);
			
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                out.println("Account with Account Number " + AccountNumber + " has been successfully deleted.");
                out.println(" --Thank you-- .");
                out.println("<h2><a href='31.html'>Home</a></h2>");



            } else {
                out.println("Account deletion failed. Please check the provided Account Number and Pin.");
                out.println("<h2><a href='31.html'>Home</a></h2>");
                out.println("<h2><a href='CloseAcc.html'>previous</a></h2>");
            }
            
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
