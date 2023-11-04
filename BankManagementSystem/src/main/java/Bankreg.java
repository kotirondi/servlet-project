import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Bankreg extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            String name = request.getParameter("Hname");
            String address = request.getParameter("add");
            String contact = request.getParameter("Con");
            String mail = request.getParameter("mail");
            String pin = request.getParameter("pin"); 

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/market";
            String username = "root";
            String dbPassword = "kotikoti12"; // Database password

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            String qry = "INSERT INTO register VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, contact);
            stmt.setString(4, mail);
            stmt.setString(5, pin);
        stmt.executeUpdate();
            con.close();

            PrintWriter out = response.getWriter();
            out.println("<h2>User Registered Successfully</h2>");
           out.println("<h2><a href='loginbank.html'>LOGIN NOW</a><h2>");


            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
