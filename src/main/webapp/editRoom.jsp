<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Admin - Edit Room</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>🏨 Edit Room</h1>

        <p>Update room information and availability</p>

    </div>


    <div class="reservation-card">

        <%
            Room room =
                    (Room) request.getAttribute("room");
        %>


        <% if (room != null) { %>

        <form action="AdminRoomsServlet"
              method="post">

            <input type="hidden"
                   name="action"
                   value="edit">

            <input type="hidden"
                   name="roomId"
                   value="<%= room.getRoomId() %>">


            <div>

                <label>Room ID</label>

                <input type="text"
                       value="<%= room.getRoomId() %>"
                       readonly>

            </div>


            <br>


            <div>

                <label>Room Number</label>

                <input type="number"
                       name="roomNumber"
                       value="<%= room.getRoomNumber() %>"
                       required>

            </div>


            <br>


            <div>

                <label>Room Type</label>

                <select name="typeId"
                        required>

                    <option value="1"
                        <%= "Deluxe Room".equals(room.getTypeName())
                            ? "selected" : "" %>>
                        Deluxe Room
                    </option>

                    <option value="2"
                        <%= "Suite".equals(room.getTypeName())
                            ? "selected" : "" %>>
                        Suite
                    </option>

                    <option value="21"
                        <%= "Presidential Suite".equals(room.getTypeName())
                            ? "selected" : "" %>>
                        Presidential Suite
                    </option>

                </select>

            </div>


            <br>


            <div>

                <label>Status</label>

                <select name="status"
                        required>

                    <option value="AVAILABLE"
                        <%= "AVAILABLE".equals(room.getStatus())
                            ? "selected" : "" %>>
                        AVAILABLE
                    </option>

                    <option value="UNAVAILABLE"
                        <%= "UNAVAILABLE".equals(room.getStatus())
                            ? "selected" : "" %>>
                        UNAVAILABLE
                    </option>

                </select>

            </div>


            <br>


            <button type="submit">

                💾 Save Changes

            </button>


            <br><br>


            <a href="AdminRoomsServlet"
               class="back-link">

                ← Back to Room Management

            </a>

        </form>


        <% } else { %>

            <p>Room information could not be found.</p>

            <a href="AdminRoomsServlet"
               class="back-link">

                ← Back to Room Management

            </a>

        <% } %>

    </div>

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body>

</html>