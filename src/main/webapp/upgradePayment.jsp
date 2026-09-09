<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.hotel.model.Reservation" %>
<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html> <html> <head> <meta charset="UTF-8"> <title>Upgrade Payment</title> <link rel="stylesheet" href="css/style.css"> </head> <body> <div class="container">
<div class="page-header">

    <h1>💳 Upgrade Payment</h1>

    <p>
        Complete the payment for your room upgrade.
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
<!-- UPGRADE SUMMARY -->
<!-- ========================= -->

<div class="reservation-card">

    <h2>Upgrade Summary</h2>

    <div class="details-grid">

        <div class="detail-item">

            <span>Current Room</span>

            <strong>
                Room <%= currentRoom.getRoomNumber() %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Current Room Type</span>

            <strong>
                <%= currentRoom.getTypeName() %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Current Price</span>

            <strong>
                Rs.
                <%= String.format(
                        "%,.0f",
                        currentRoom.getBasePrice()) %>
                /night
            </strong>

        </div>


        <div class="detail-item">

            <span>New Room</span>

            <strong>
                Room <%= newRoom.getRoomNumber() %>
            </strong>

        </div>


        <div class="detail-item">

            <span>New Room Type</span>

            <strong>
                <%= newRoom.getTypeName() %>
            </strong>

        </div>


        <div class="detail-item">

            <span>New Price</span>

            <strong>
                Rs.
                <%= String.format(
                        "%,.0f",
                        newRoom.getBasePrice()) %>
                /night
            </strong>

        </div>


        <div class="detail-item">

            <span>Number of Nights</span>

            <strong>
                <%= numberOfNights %>
            </strong>

        </div>


        <div class="detail-item">

            <span>Additional per Night</span>

            <strong>
                Rs.
                <%= String.format(
                        "%,.0f",
                        priceDifference) %>
            </strong>

        </div>


        <div class="total-item">

            <span>Additional Amount to Pay</span>

            <strong>
                Rs.
                <%= String.format(
                        "%,.0f",
                        additionalAmount) %>
            </strong>

        </div>

    </div>


    <br>


    <p>
        You only pay the difference between your
        current room and the upgraded room.
    </p>

</div>


<br>


<!-- ========================= -->
<!-- PAYMENT -->
<!-- ========================= -->

<div class="reservation-card">

    <h2>Select Payment Method</h2>


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


        <button
            type="submit"
            class="btn"
        >

            💳 Pay Rs.
            <%= String.format(
                    "%,.0f",
                    additionalAmount) %>
            & Upgrade Room

        </button>

    </form>

</div>


<br>


<a
    href="UpgradeRoomServlet?reservationId=<%= reservation.getReservationId() %>"
    class="back-link"
>

    ← Cancel Upgrade

</a>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>