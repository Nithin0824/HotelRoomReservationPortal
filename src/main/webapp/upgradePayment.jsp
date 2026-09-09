<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.hotel.model.Reservation" %>
<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Upgrade Payment</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>💳 Upgrade Payment</h1>

        <p>
            Review your room upgrade before making the additional payment.
        </p>

    </div>


    <%

        Reservation reservation =
                (Reservation) request.getAttribute(
                        "reservation");

        Room currentRoom =
                (Room) request.getAttribute(
                        "currentRoom");

        Room newRoom =
                (Room) request.getAttribute(
                        "newRoom");

        Long numberOfNights =
                (Long) request.getAttribute(
                        "numberOfNights");

        Double priceDifference =
                (Double) request.getAttribute(
                        "priceDifference");

        Double additionalAmount =
                (Double) request.getAttribute(
                        "additionalAmount");

    %>


    <!-- ========================= -->
    <!-- RESERVATION DETAILS -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>Reservation Details</h2>

        <div class="details-grid">

            <div class="detail-item">

                <span>Reservation ID</span>

                <strong>
                    <%= reservation.getReservationId() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Guest Name</span>

                <strong>
                    <%= reservation.getGuestName() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Check-In</span>

                <strong>
                    <%= reservation.getCheckIn() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Check-Out</span>

                <strong>
                    <%= reservation.getCheckOut() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Number of Nights</span>

                <strong>
                    <%= numberOfNights %>
                </strong>

            </div>

        </div>

    </div>


    <br>


    <!-- ========================= -->
    <!-- CURRENT ROOM -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>Current Room</h2>

        <div class="details-grid">

            <div class="detail-item">

                <span>Room Number</span>

                <strong>
                    <%= currentRoom.getRoomNumber() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Room Type</span>

                <strong>
                    <%= currentRoom.getTypeName() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Price per Night</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            currentRoom.getBasePrice()) %>
                </strong>

            </div>

        </div>

    </div>


    <br>


    <!-- ========================= -->
    <!-- NEW ROOM -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>New Room</h2>

        <div class="details-grid">

            <div class="detail-item">

                <span>Room Number</span>

                <strong>
                    <%= newRoom.getRoomNumber() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Room Type</span>

                <strong>
                    <%= newRoom.getTypeName() %>
                </strong>

            </div>


            <div class="detail-item">

                <span>New Price per Night</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            newRoom.getBasePrice()) %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Additional Cost per Night</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            priceDifference) %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Additional Payment</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            additionalAmount) %>
                </strong>

            </div>

        </div>

    </div>


    <br>


    <!-- ========================= -->
    <!-- PAYMENT -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>Payment</h2>

        <p>
            You only need to pay the difference for the room upgrade.
        </p>


        <form
            action="UpgradePaymentServlet"
            method="post"
        >

            <input
                type="hidden"
                name="reservationId"
                value="<%= reservation.getReservationId() %>"
            >


            <input
                type="hidden"
                name="newRoomId"
                value="<%= newRoom.getRoomId() %>"
            >


            <div class="form-group">

                <label for="paymentMethod">
                    Payment Method
                </label>

                <select
                    id="paymentMethod"
                    name="paymentMethod"
                    required
                >

                    <option value="">
                        -- Select Payment Method --
                    </option>

                    <option value="CASH">
                        Cash
                    </option>

                    <option value="CARD">
                        Card
                    </option>

                    <option value="UPI">
                        UPI
                    </option>

                </select>

            </div>


            <br>


            <div class="payment-summary">

                <h3>
                    Additional Amount:
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            additionalAmount) %>
                </h3>

            </div>


            <br>


            <button
                type="submit"
                class="btn"
            >

                💳 Confirm Upgrade & Pay

            </button>

        </form>

    </div>


    <br>


    <a
        href="UpgradeRoomServlet?reservationId=<%= reservation.getReservationId() %>"
        class="back-link"
    >

        ← Back to Available Upgrade Rooms

    </a>


    <br><br>


    <a
        href="ViewReservationServlet?reservationId=<%= reservation.getReservationId() %>"
        class="back-link"
    >

        ← Back to Reservation

    </a>

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body>

</html>