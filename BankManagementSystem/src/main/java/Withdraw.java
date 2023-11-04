import java.io.*;
import java.sql.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Withdraw extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            
            String AccountNumber = request.getParameter("accno");
            String Pin = request.getParameter("pin");
            String WithD = request.getParameter("wamount");

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/banking";
            String username = "root";
            String dbPassword = "kotikoti12";

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, dbPassword);
            String qry = "SELECT * FROM Account WHERE AccountNo=? AND Password=?";
		   
            PreparedStatement stmt = con.prepareStatement(qry);
            stmt.setString(1, AccountNumber);
            stmt.setString(2, Pin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int storedAccNo = rs.getInt("AccountNo");
                String storedUN = rs.getString("Password");

                if (AccountNumber.equals(String.valueOf(storedAccNo)) && Pin.equals(storedUN)) {
                    double withdrawAmount = Double.parseDouble(WithD);

                    String balanceQuery = "SELECT BALANCE FROM Account WHERE AccountNo=? AND Password=?";
                    PreparedStatement balanceStmt = con.prepareStatement(balanceQuery);
                    balanceStmt.setString(1, AccountNumber);
                    balanceStmt.setString(2, Pin);
                    ResultSet balanceRs = balanceStmt.executeQuery();

                    if (balanceRs.next()) {
                        double updatedBalance = balanceRs.getDouble("BALANCE");
                        if (withdrawAmount <= updatedBalance) {
                            String updateQuery = "UPDATE Account SET BALANCE=BALANCE-? WHERE AccountNo=? AND Password=?";
                            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                            updateStmt.setDouble(1, withdrawAmount);
                            updateStmt.setString(2, AccountNumber);
                            updateStmt.setString(3, Pin);
                            updateStmt.executeUpdate();
                             double remainingBalance = updatedBalance - withdrawAmount;


                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Withdrawal Result</title>");
                            out.println("</head>");
                            out.println("<body>");

                            out.println("<h2>Withdrawal Summary</h2>");
                            out.println("<p>Account Number: " + storedAccNo + "</p>");
                            out.println("<p>Withdrawal Amount: $" + withdrawAmount + "</p>");
                            out.println("<p>Withdrawal successful!</p>");
                            out.println("<p>Remaining Balance: $" + remainingBalance + "</p>");


                            out.println("</body>");
                            out.println("</html>");

                            updateStmt.close();
                        } else {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>Withdrawal Result</title>");
                            out.println("</head>");
                            out.println("<body>");

                            out.println("<p>Insufficient balance for withdrawal.</p>");

                            out.println("</body>");
                            out.println("</html>");
                        }
                    }

                    balanceStmt.close();
                } else {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Withdrawal Result</title>");
                    out.println("</head>");
                    out.println("<body>");

                    out.println("<p>Invalid account number or PIN.</p>");

                    out.println("</body>");
                    out.println("</html>");
                }
            }

            stmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
