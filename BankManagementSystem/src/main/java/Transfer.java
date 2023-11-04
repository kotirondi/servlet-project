import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Transfer extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            
            String AccountNumber = request.getParameter("accno");
            String Pin = request.getParameter("pin");
            String TargetAccountNumber = request.getParameter("taccno");
            String TransferAmount = request.getParameter("tamount");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/banking";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            
            // Validate source account
            String sourceAccountQuery = "SELECT * FROM Account WHERE AccountNo=? AND Password=?";
            PreparedStatement sourceAccountStmt = con.prepareStatement(sourceAccountQuery);
            sourceAccountStmt.setString(1, AccountNumber);
            sourceAccountStmt.setString(2, Pin);
            ResultSet sourceAccountRs = sourceAccountStmt.executeQuery();

            if (sourceAccountRs.next()) {
                double transferAmount = Double.parseDouble(TransferAmount);

                // Check if source account has sufficient balance
                double sourceAccountBalance = sourceAccountRs.getDouble("BALANCE");
                if (transferAmount <= sourceAccountBalance) {
                    // Validate target account
                    String targetAccountQuery = "SELECT * FROM Account WHERE AccountNo=?";
                    PreparedStatement targetAccountStmt = con.prepareStatement(targetAccountQuery);
                    targetAccountStmt.setString(1, TargetAccountNumber);
                    ResultSet targetAccountRs = targetAccountStmt.executeQuery();

                    if (targetAccountRs.next()) {
                        // Perform the money transfer
                        String updateSourceQuery = "UPDATE Account SET BALANCE=BALANCE-? WHERE AccountNo=? AND Password=?";
                        PreparedStatement updateSourceStmt = con.prepareStatement(updateSourceQuery);
                        updateSourceStmt.setDouble(1, transferAmount);
                        updateSourceStmt.setString(2, AccountNumber);
                        updateSourceStmt.setString(3, Pin);
                        updateSourceStmt.executeUpdate();
                        updateSourceStmt.close();

                        String updateTargetQuery = "UPDATE Account SET BALANCE=BALANCE+? WHERE AccountNo=?";
                        PreparedStatement updateTargetStmt = con.prepareStatement(updateTargetQuery);
                        updateTargetStmt.setDouble(1, transferAmount);
                        updateTargetStmt.setString(2, TargetAccountNumber);
                        updateTargetStmt.executeUpdate();
                        updateTargetStmt.close();
						double remainingBalance = sourceAccountBalance - transferAmount;

                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Transfer Result</title>");
                        out.println("</head>");
                        out.println("<body>");

                        out.println("<h2>Transfer Details</h2>");
                        out.println("<p>Source Account: " + AccountNumber + "</p>");
                        out.println("<p>Target Account: " + TargetAccountNumber + "</p>");
                        out.println("<p>Transfer Amount: $" + transferAmount + "</p>");
                        out.println("<p>Transfer successful!</p>");
						 out.println("<p>Remaining Balance: $" + remainingBalance + "</p>");

                        out.println("</body>");
                        out.println("</html>");
                    } else {
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Transfer Result</title>");
                        out.println("</head>");
                        out.println("<body>");

                        out.println("<p>Invalid target account number.</p>");

                        out.println("</body>");
                        out.println("</html>");
                    }

                    targetAccountStmt.close();
                } else {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Transfer Result</title>");
                    out.println("</head>");
                    out.println("<body>");

                    out.println("<p>Insufficient balance for transfer.</p>");

                    out.println("</body>");
                    out.println("</html>");
                }
            } else {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Transfer Result</title>");
                out.println("</head>");
                out.println("<body>");

                out.println("<p>Invalid account number or PIN.</p>");

                out.println("</body>");
                out.println("</html>");
            }

            sourceAccountStmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
