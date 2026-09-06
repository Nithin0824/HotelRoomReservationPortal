<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Admin - Reservations</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>📋 All Reservations</h1>

        <p>View and monitor hotel reservations</p>

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

                        <th>Reservation ID</th>
                        <th>Guest Name</th>
                        <th>Room</th>
                        <th>Room Type</th>
                        <th>Check-in</th>
                        <th>Check-out</th>
                        <th>Guests</th>
                        <th>Status</th>

                    </tr>

                </thead>


                <tbody>

                <%
                    while (resultSet.next()) {
                %>

                    <tr>

                        <td>
                            <%= resultSet.getInt("RESERVATION_ID") %>
                        </td>

                        <td>
                            <%= resultSet.getString("GUEST_NAME") %>
                        </td>

                        <td>
                            <%= resultSet.getInt("ROOM_NUMBER") %>
                        </td>

                        <td>
                            <%= resultSet.getString("TYPE_NAME") %>
                        </td>

                        <td>
                            <%= resultSet.getDate("CHECK_IN") %>
                        </td>

                        <td>
                            <%= resultSet.getDate("CHECK_OUT") %>
                        </td>

                        <td>
                            <%= resultSet.getInt("GUESTS") %>
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