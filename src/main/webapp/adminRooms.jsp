<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Room" %>

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

        <p>Add, edit and manage hotel room availability</p>

    </div>


    <div class="reservation-card">

        <h2>Room Inventory</h2>
        
         <div style="margin-bottom:20px;">

    <a href="addRoom.jsp" class="back-link">
        ➕ Add New Room
    </a>

</div>
         

        <%
            List<Room> rooms =
                    (List<Room>) request.getAttribute("rooms");
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
                        <th>Action</th>

                    </tr>

                </thead>


                <tbody>

                <%
                    if (rooms != null && !rooms.isEmpty()) {

                        for (Room room : rooms) {
                %>

                    <tr>

                        <td>
                            <%= room.getRoomId() %>
                        </td>

                        <td>
                            <%= room.getRoomNumber() %>
                        </td>

                        <td>
                            <%= room.getTypeName() %>
                        </td>

                        <td>
                            Rs.
                            <%= String.format(
                                "%,.0f",
                                room.getBasePrice()
                            ) %>
                        </td>

                        <td>
                            <strong>
                                <%= room.getStatus() %>
                            </strong>
                        </td>

                        <td>

                            <a href="AdminRoomsServlet?action=edit&roomId=<%= room.getRoomId() %>"
                               class="back-link">

                                Edit

                            </a>

                        </td>

                    </tr>

                <%
                        }

                    } else {
                %>

                    <tr>

                        <td colspan="6"
                            style="text-align:center;">

                            No rooms found.

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
