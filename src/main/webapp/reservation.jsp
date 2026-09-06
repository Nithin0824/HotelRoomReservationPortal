<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.hotel.DBConnection" %>

<!DOCTYPE html> <html> <head>
<meta charset="UTF-8">

<title>Hotel Reservation</title>

<link rel="stylesheet" href="css/style.css">

</head> <body> <div class="container">
<div class="page-header">

    <h1>🏨 Hotel Reservation</h1>

    <p>Review your reservation details</p>

</div>


<%
String roomId = (String) request.getAttribute("roomId");

int roomNumber = 0;
String typeName = "";
double price = 0;

String sql =
"SELECT r.ROOM_NUMBER, rt.TYPE_NAME, rt.BASE_PRICE " +
"FROM HOTEL_ROOMS r " +
"JOIN ROOM_TYPES rt ON r.TYPE_ID = rt.TYPE_ID " +
"WHERE r.ROOM_ID = ?";

try (
Connection connection = DBConnection.getConnection();
PreparedStatement statement = connection.prepareStatement(sql)
) {

statement.setInt(1, Integer.parseInt(roomId));

try (ResultSet resultSet = statement.executeQuery()) {

    if (resultSet.next()) {

        roomNumber = resultSet.getInt("ROOM_NUMBER");

        typeName = resultSet.getString("TYPE_NAME");

        price = resultSet.getDouble("BASE_PRICE");

    }
}


} catch (Exception e) {

out.println("<p>Error loading room details.</p>");

e.printStackTrace();


}

java.time.LocalDate checkInDate =
java.time.LocalDate.parse(
(String) request.getAttribute("checkIn")
);

java.time.LocalDate checkOutDate =
java.time.LocalDate.parse(
(String) request.getAttribute("checkOut")
);

long numberOfNights =
java.time.temporal.ChronoUnit.DAYS.between(
checkInDate,
checkOutDate
);

double totalAmount =
numberOfNights * price;

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
                <%= request.getAttribute("checkIn") %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Check-out</span>

            <strong>
                <%= request.getAttribute("checkOut") %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Guests</span>

            <strong>
                <%= request.getAttribute("guests") %>
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
               value="<%= request.getAttribute("checkIn") %>">


        <input type="hidden"
               name="checkOut"
               value="<%= request.getAttribute("checkOut") %>">


        <input type="hidden"
               name="guests"
               value="<%= request.getAttribute("guests") %>">


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


    <a href="welcome.jsp" class="back-link">

        ← Back to Hotel Reservation Portal

    </a>


</div>

</div> </body> </html>