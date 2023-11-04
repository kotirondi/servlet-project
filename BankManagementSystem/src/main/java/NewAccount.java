import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewAccount extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            String uname = request.getParameter("user");
            String password = request.getParameter("pass");
            String amount= request.getParameter("amo");
            String address = request.getParameter("add");
            String mail = request.getParameter("mail");
            String aadhar=request.getParameter("aadh");
            String dob=request.getParameter("dob");
            String contact=request.getParameter("con");
            String age=request.getParameter("age");
            String gender=request.getParameter("gender");
            String occupation=request.getParameter("occ");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/banking";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            String qry = "INSERT INTO Account(username, password,amount,address,email, aadharno, DOB,phone, age,gender,occupation) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.setString(1, uname);
            stmt.setString(2, password);
            stmt.setString(3, amount);
            stmt.setString(4,address);
            stmt.setString(5, mail);
            stmt.setString(6, aadhar);
            stmt.setString(7, dob);
            stmt.setString(8, contact);
            stmt.setString(9,age);
            stmt.setString(10, gender);
            stmt.setString(11,occupation);
        stmt.executeUpdate();
            con.close();

            PrintWriter out = response.getWriter();
           
           // response.sendRedirect("loginbank.html");
           //  out.println("<h2><a href="31.html"</h2>");
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>New Account</title>");
                            out.println("</head>");
                            out.println("<body>");

                            out.println("<h2>Account Creation</h2>");
                           
                            out.println("<p> your account created successful!</p>");
                            
                            out.println("<h2><a href='31.html'>Home</a></h2>");

                            out.println("</body>");
                            out.println("</html>");
              


            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
