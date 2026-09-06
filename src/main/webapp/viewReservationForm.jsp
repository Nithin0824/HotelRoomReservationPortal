<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head>
<meta charset="UTF-8">

<title>View My Reservation</title>

<link rel="stylesheet" href="css/style.css">

</head> <body> <div class="container">
<div class="page-header">

    <h1>🔎 View My Reservation</h1>

    <p>Retrieve your hotel reservation details</p>

</div>


<div class="reservation-card lookup-card">

    <h2>Reservation Lookup</h2>

    <p>
        Enter your Reservation ID to view your booking details.
    </p>


    <form
        action="ViewReservationServlet"
        method="get"
    >

        <div class="form-group">

            <label for="reservationId">
                Reservation ID
            </label>

            <input
                type="number"
                id="reservationId"
                name="reservationId"
                min="1"
                placeholder="Enter your Reservation ID"
                required
            >

        </div>


        <button
            type="submit"
            class="btn"
        >

            View Reservation

        </button>

    </form>


    <br>


    <a
        href="welcome.jsp"
        class="back-link"
    >

        ← Back to Hotel Reservation Portal

    </a>

</div>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>