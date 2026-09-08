<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.hotel.dao.ReservationDAO.RoomDetails" %>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Hotel Reservation</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

<div class="page-header">

    <h1>🏨 Hotel Reservation</h1>

    <p>Review your reservation details</p>

</div>


<%

RoomDetails roomDetails =
        (RoomDetails) request.getAttribute("roomDetails");

String roomId =
        (String) request.getAttribute("roomId");

String checkIn =
        (String) request.getAttribute("checkIn");

String checkOut =
        (String) request.getAttribute("checkOut");

String guests =
        (String) request.getAttribute("guests");

int roomNumber =
        roomDetails.getRoomNumber();

String typeName =
        roomDetails.getTypeName();

double price =
        roomDetails.getPrice();

long numberOfNights =
        (Long) request.getAttribute("numberOfNights");

double totalAmount =
        (Double) request.getAttribute("totalAmount");

%>


<div class="reservation-card">

    <h2>Reservation Details</h2>


    <div class="details-grid">


        <div class="detail-item">

            <span>Room Number</span>

            <strong>
                <%= roomNumber %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Room Type</span>

            <strong>
                <%= typeName %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Price per Night</span>

            <strong>
                Rs. <%= String.format("%,.0f", price) %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Check-in</span>

            <strong>
                <%= checkIn %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Check-out</span>

            <strong>
                <%= checkOut %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Guests</span>

            <strong>
                <%= guests %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Number of Nights</span>

            <strong>
                <%= numberOfNights %>
            </strong>

        </div>


        <div class="total-item">

            <span>Total Amount</span>

            <strong>
                Rs. <%= String.format("%,.0f", totalAmount) %>
            </strong>

        </div>


    </div>


    <hr>


    <h2>Guest Information</h2>


    <form action="ReservationServlet" method="post">


        <input type="hidden"
               name="roomId"
               value="<%= roomId %>">


        <input type="hidden"
               name="checkIn"
               value="<%= checkIn %>">


        <input type="hidden"
               name="checkOut"
               value="<%= checkOut %>">


        <input type="hidden"
               name="guests"
               value="<%= guests %>">


        <div class="form-group">

            <label for="guestName">
                Guest Name
            </label>

            <input type="text"
                   id="guestName"
                   name="guestName"
                   placeholder="Enter guest name"
                   required>

        </div>


        <button type="submit" class="btn">

            Confirm Reservation

        </button>


    </form>


    <br>


    <a href="welcome.jsp"
       class="back-link">

        ← Back to Hotel Reservation Portal

    </a>


</div>

</div>

</body>

</html>
