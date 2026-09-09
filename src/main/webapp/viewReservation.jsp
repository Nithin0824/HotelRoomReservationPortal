<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Reservation Details</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>


<div class="container">

    <div class="page-header">

        <h1>📋 Reservation Details</h1>

        <p>View and manage your hotel reservation</p>

    </div>


    <div class="reservation-card">


        <div class="details-grid">


            <div class="detail-item">

                <span>Reservation ID</span>

                <strong>
                    <%= request.getAttribute("reservationId") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Guest Name</span>

                <strong>
                    <%= request.getAttribute("guestName") %>
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
                    <%= request.getAttribute("status") %>
                </strong>

            </div>


        </div>


        <hr>


        <%
            String status =
                    (String) request.getAttribute("status");
        %>


        <!-- ========================= -->
        <!-- PENDING RESERVATION -->
        <!-- ========================= -->

        <%
        if ("PENDING".equals(status)) {
        %>


        <div class="success-message">

            <h2>⏳ Payment Pending</h2>

            <p>
                Your reservation has been created successfully,
                but payment has not been completed yet.
            </p>

            <p>
                Please complete the payment to confirm
                your reservation.
            </p>

        </div>


        <br>


        <div class="confirmation-actions">

            <form
                action="PaymentServlet"
                method="get"
            >

                <input
                    type="hidden"
                    name="reservationId"
                    value="<%= request.getAttribute("reservationId") %>"
                >

                <input
                    type="hidden"
                    name="amount"
                    value="<%= request.getAttribute("amount") %>"
                >


                <button
                    type="submit"
                    class="btn"
                >

                    💳 Pay Now

                </button>

            </form>

        </div>


        <br>


        <%
        } else if ("BOOKED".equals(status)) {
        %>


        <!-- ========================= -->
        <!-- BOOKED RESERVATION -->
        <!-- ========================= -->

        <div class="success-message">

            <h2>✓ Reservation Confirmed</h2>

            <p>
                Your payment has been completed and
                your reservation is confirmed.
            </p>

        </div>


        <br>


        <!-- ========================= -->
        <!-- UPGRADE ROOM -->
        <!-- ========================= -->

        <div class="cancel-section">

            <h2>🔄 Switch / Upgrade Room</h2>

            <p>
                Want a better room?
                You can switch to a higher-priced
                available room by paying only the
                remaining difference.
            </p>


            <form
                action="UpgradeRoomServlet"
                method="get"
            >

                <input
                    type="hidden"
                    name="reservationId"
                    value="<%= request.getAttribute("reservationId") %>"
                >


                <button
                    type="submit"
                    class="btn"
                >

                    🔄 View Upgrade Rooms

                </button>

            </form>

        </div>


        <br>


        <!-- ========================= -->
        <!-- CANCEL RESERVATION -->
        <!-- ========================= -->

        <div class="cancel-section">

            <h2>Cancel Reservation</h2>

            <p>
                If you no longer need this reservation,
                you can cancel it below.
            </p>


            <form
                action="CancelReservationServlet"
                method="post"
            >

                <input
                    type="hidden"
                    name="reservationId"
                    value="<%= request.getAttribute("reservationId") %>"
                >


                <button
                    type="submit"
                    class="cancel-button"
                    onclick="return confirm('Are you sure you want to cancel this reservation?');"
                >

                    Cancel Reservation

                </button>

            </form>

        </div>


        <%
        } else if ("CANCELLED".equals(status)) {
        %>


        <!-- ========================= -->
        <!-- CANCELLED RESERVATION -->
        <!-- ========================= -->

        <div class="cancelled-message">

            <h2>Reservation Cancelled</h2>

            <p>
                This reservation has already been cancelled.
            </p>

        </div>


        <%
        } else {
        %>


        <!-- ========================= -->
        <!-- UNKNOWN STATUS -->
        <!-- ========================= -->

        <div class="cancelled-message">

            <h2>Reservation Status</h2>

            <p>
                Current reservation status:
                <strong>
                    <%= status %>
                </strong>
            </p>

        </div>


        <%
        }
        %>


        <br>


        <a
            href="welcome.jsp"
            class="back-link"
        >

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