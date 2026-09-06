<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head> <meta charset="UTF-8">
 <title>Reservation Cancelled</title>
<link rel="stylesheet" href="css/style.css">

</head> <body> <div class="container">
<div class="cancellation-card">

    <div class="cancellation-icon">
        ✓
    </div>

    <h1>Reservation Cancelled</h1>

    <p class="cancellation-text">
        Your reservation has been successfully cancelled.
    </p>

    <div class="cancelled-details">

        <div class="detail-item">

            <span>Reservation ID</span>

            <strong>
                <%= request.getAttribute("reservationId") %>
            </strong>

        </div>

    </div>

    <p class="room-message">
        The room is now available for new bookings.
    </p>

    <hr>

    <div class="confirmation-actions">

        <a href="viewReservationForm.jsp" class="btn">
            🔎 View My Reservation
        </a>

        <a href="welcome.jsp" class="back-link">
            ← Back to Hotel Reservation Portal
        </a>

    </div>

</div>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>