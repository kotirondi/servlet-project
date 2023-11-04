
import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Banklogin extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            
            String nme = request.getParameter("name");
            String pin = request.getParameter("pin");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/market";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            String qry = "select * from register where name=?";

            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.setString(1, nme);

			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String dbpwd = rs.getString("Pin");
				if (pin.equals(dbpwd)) {
					out.println("<h2><a href='31.html'>Home</a></h2>");
				}
				
				else {
					out.println("<h2>user credentials are incorrect</h2>");
                                       out.println("<h2><a href='loginbank.html'>go to login</a></h2>");
                                       out.println("<h2><a href='account.html'>go to register</a></h2>");



				}
			} 
			else {
				out.println("<h2>user does not exist</h2>");
                                out.println("<h2><a href='account.html'>go to register</a></h2>");



			
			}
            con.close();
               } catch (Exception e){
			
            System.out.println(e);
        }
    }
}
