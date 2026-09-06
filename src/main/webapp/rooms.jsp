<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Available Rooms</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="hotel-header">

    <h1>🏨 Available Rooms</h1>

    <p>Choose the perfect room for your stay.</p>

</div>


<div class="container">

    <div class="search-summary">

        <h2>Your Search</h2>

        <div class="details">

            <div class="detail">

                <strong>Check-in:</strong>

                <%= request.getAttribute("checkIn") %>

            </div>


            <div class="detail">

                <strong>Check-out:</strong>

                <%= request.getAttribute("checkOut") %>

            </div>


            <div class="detail">

                <strong>Guests:</strong>

                <%= request.getAttribute("guests") %>

            </div>

        </div>

    </div>


    <h2 class="rooms-title">
        Available Rooms
    </h2>


    <div class="rooms">

<%

List<Room> rooms =
        (List<Room>) request.getAttribute("rooms");

boolean foundRooms = false;

if (rooms != null && !rooms.isEmpty()) {

    for (Room room : rooms) {

        foundRooms = true;

        int roomId =
                room.getRoomId();

        String roomNumber =
                room.getRoomNumber();

        String typeName =
                room.getTypeName();

        double price =
                room.getBasePrice();

%>

        <div class="room-card">


            <div class="room-icon">
                🛏️
            </div>


            <h3>
                <%= typeName %>
            </h3>


            <p class="room-number">
                Room <%= roomNumber %>
            </p>


            <div class="price">

                Rs. <%= String.format("%,.0f", price) %>

                <span>
                    per night
                </span>

            </div>


            <a
                class="select-button"
                href="ReservationServlet?roomId=<%= roomId %>&checkIn=<%= request.getAttribute("checkIn") %>&checkOut=<%= request.getAttribute("checkOut") %>&guests=<%= request.getAttribute("guests") %>">

                Select Room

            </a>


        </div>


<%

    }

}

if (!foundRooms) {

%>

        <div class="no-rooms">

            <div class="room-icon">
                🏨
            </div>

            <h2>No Rooms Available</h2>

            <p>
                Sorry, there are no rooms available
                for your selected dates.
            </p>

        </div>

<%

}

%>

    </div>


    <a
        href="welcome.jsp"
        class="back-link">

        ← Back to Hotel Reservation Portal

    </a>

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body>

</html>