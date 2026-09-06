<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Admin Dashboard</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>🏨 Admin Dashboard</h1>

        <p>Hotel Reservation Portal Administration</p>

    </div>


    <div class="reservation-card">

        <h2>Welcome, Admin</h2>

        <p>
            Manage hotel reservations, rooms, payments
            and view system information.
        </p>


        <hr>


        <div class="confirmation-actions">

            <a href="AdminReservationsServlet" class="btn">
                📋 View Reservations
            </a>

            <a href="AdminRoomsServlet" class="btn">
                🏨 Manage Rooms
            </a>

            <a href="AdminPaymentsServlet" class="btn">
                💳 View Payments
            </a>

        </div>


        <br>


        <a href="welcome.jsp" class="back-link">
            ← Back to Hotel Reservation Portal
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