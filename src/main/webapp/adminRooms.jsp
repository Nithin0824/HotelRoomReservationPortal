<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Admin - Room Management</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>🏨 Room Management</h1>

        <p>View hotel room inventory and pricing</p>

    </div>


    <div class="reservation-card">

        <%
            java.sql.ResultSet resultSet =
                    (java.sql.ResultSet) request.getAttribute("resultSet");
        %>


        <div style="overflow-x:auto;">

            <table border="1"
                   cellpadding="10"
                   cellspacing="0"
                   style="width:100%; border-collapse:collapse;">

                <thead>

                    <tr>

                        <th>Room ID</th>
                        <th>Room Number</th>
                        <th>Room Type</th>
                        <th>Price per Night</th>
                        <th>Status</th>

                    </tr>

                </thead>


                <tbody>

                <%
                    while (resultSet.next()) {
                %>

                    <tr>

                        <td>
                            <%= resultSet.getInt("ROOM_ID") %>
                        </td>

                        <td>
                            <%= resultSet.getInt("ROOM_NUMBER") %>
                        </td>

                        <td>
                            <%= resultSet.getString("TYPE_NAME") %>
                        </td>

                        <td>
                            Rs.
                            <%= String.format(
                                "%,.0f",
                                resultSet.getDouble("BASE_PRICE")
                            ) %>
                        </td>

                        <td>
                            <strong>
                                <%= resultSet.getString("STATUS") %>
                            </strong>
                        </td>

                    </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        </div>


        <br>


        <a href="adminDashboard.jsp"
           class="back-link">

            ← Back to Admin Dashboard

        </a>

    </div>

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body>

</html>