<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head> <meta charset="UTF-8"> <title>Payment Management</title> <link rel="stylesheet" href="css/style.css"> </head> <body> <div class="container">
<div class="page-header">

    <h1>💳 Payment Management</h1>

    <p>View and monitor hotel payment transactions</p>

</div>

<div class="reservation-card">

    <table>

        <thead>

            <tr>

                <th>Payment ID</th>
                <th>Reservation ID</th>
                <th>Payment Method</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Payment Date</th>

            </tr>

        </thead>

        <tbody>

        <%
            java.sql.ResultSet resultSet =
                    (java.sql.ResultSet) request.getAttribute("resultSet");

            while (resultSet != null && resultSet.next()) {
        %>

            <tr>

                <td>
                    <%= resultSet.getInt("PAYMENT_ID") %>
                </td>

                <td>
                    <%= resultSet.getInt("RESERVATION_ID") %>
                </td>

                <td>
                    <%= resultSet.getString("PAYMENT_METHOD") %>
                </td>

                <td>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            resultSet.getDouble("AMOUNT")
                        ) %>
                </td>

                <td>
                    <%= resultSet.getString("PAYMENT_STATUS") %>
                </td>

                <td>
                    <%= resultSet.getTimestamp("PAYMENT_DATE") %>
                </td>

            </tr>

        <%
            }
        %>

        </tbody>

    </table>

    <br>

    <a href="adminDashboard.jsp" class="back-link">
        ← Back to Admin Dashboard
    </a>

</div>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>