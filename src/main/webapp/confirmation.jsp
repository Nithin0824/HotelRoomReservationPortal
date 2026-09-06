<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Reservation Created</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>


<div class="container">

    <div class="confirmation-card">


        <div class="confirmation-icon">
            ✓
        </div>


        <h1>Reservation Created!</h1>


        <p class="confirmation-text">

            Thank you,
            <strong>
                <%= request.getAttribute("guestName") %>
            </strong>.

        </p>


        <p>
            Your reservation has been successfully created.
        </p>


        <p>
            Your reservation is currently
            <strong>PENDING</strong>.
            Please complete payment to confirm your booking.
        </p>


        <hr>


        <h2>Reservation Details</h2>


        <div class="details-grid">


            <div class="detail-item">

                <span>Reservation ID</span>

                <strong>
                    <%= request.getAttribute("reservationId") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Room Number</span>

                <strong>
                    <%= request.getAttribute("roomNumber") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Room Type</span>

                <strong>
                    <%= request.getAttribute("typeName") %>
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
                    <%= request.getAttribute("numberOfNights") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Price per Night</span>

                <strong>

                    Rs.
                    <%= String.format(
                        "%,.0f",
                        (Double) request.getAttribute("price")
                    ) %>

                </strong>

            </div>


            <div class="detail-item">

                <span>Status</span>

                <strong>
                    PENDING
                </strong>

            </div>


            <div class="total-item">

                <span>Total Amount</span>

                <strong>

                    Rs.
                    <%= String.format(
                        "%,.0f",
                        (Double) request.getAttribute("totalAmount")
                    ) %>

                </strong>

            </div>


        </div>


        <hr>


        <div class="confirmation-actions">


            <!-- Pay for the existing reservation -->

            <a
                href="PaymentServlet?reservationId=<%= request.getAttribute("reservationId") %>&amount=<%= request.getAttribute("totalAmount") %>"
                class="btn"
            >

                💳 Pay Now

            </a>


            <!-- View reservation later -->

            <a
                href="viewReservationForm.jsp"
                class="btn"
            >

                🔎 View My Reservation

            </a>


            <!-- Return to portal -->

            <a
                href="welcome.jsp"
                class="back-link"
            >

                ← Back to Hotel Reservation Portal

            </a>


        </div>


    </div>

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>


</body>

</html>