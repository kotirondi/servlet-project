import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Report extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        PrintWriter out = null;
        Connection con = null;
        PreparedStatement checkAccountStmt = null;
        PreparedStatement updateReportStmt = null;
        ResultSet accountResultSet = null; // Declare it here and initialize to null
        
        try {
            out = response.getWriter();
            String accountno = request.getParameter("accno");
            String report = request.getParameter("report");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/banking";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            con = DriverManager.getConnection(url, username, dbPassword);

            String checkAccountQuery = "SELECT * FROM Account WHERE AccountNo = ?";
            checkAccountStmt = con.prepareStatement(checkAccountQuery);
            checkAccountStmt.setString(1, accountno);
            accountResultSet = checkAccountStmt.executeQuery();

            if (accountResultSet.next()) {
                // Update the report where accountno matches
                String updateReportQuery = "UPDATE Account SET report = ? WHERE AccountNo = ?";
                updateReportStmt = con.prepareStatement(updateReportQuery);
                updateReportStmt.setString(1, report);
                updateReportStmt.setString(2, accountno);
                int rowsAffected = updateReportStmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Data updated successfully
                    // You can add a response message or redirect to a success page here
                    out.println("Report updated successfully.");
                    out.println("<h2><a href='31.html'>Home</a></h2>");
                } else {
                    // Update failed
                    // You can handle this situation accordingly
                    out.println("Report update failed.");
					out.println("<h2><a href='Report.html'>Back</a></h2>");
                }
            } else {
                // Account does not exist, handle this situation
                // You can add a response message or redirect to an error page here
                out.println("Account not found.");
				out.println("<h2><a href='Report.html'>Back</a></h2>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here and provide an appropriate response
        } finally {
            try {
                if (accountResultSet != null) {
                    accountResultSet.close();
                }
                if (checkAccountStmt != null) {
                    checkAccountStmt.close();
                }
                if (updateReportStmt != null) {
                    updateReportStmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
