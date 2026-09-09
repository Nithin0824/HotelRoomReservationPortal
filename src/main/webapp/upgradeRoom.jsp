<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Reservation" %>
<%@ page import="com.hotel.model.Room" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Upgrade Room</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">

    <div class="page-header">

        <h1>🔄 Upgrade Your Room</h1>

        <p>
            Choose a higher-priced room for your reservation.
        </p>

    </div>


    <%
        Reservation reservation =
                (Reservation) request.getAttribute(
                        "reservation");

        Room currentRoom =
                (Room) request.getAttribute(
                        "currentRoom");

        List<Room> upgradeRooms =
                (List<Room>) request.getAttribute(
                        "upgradeRooms");


        long numberOfNights =
                java.time.temporal.ChronoUnit.DAYS.between(
                        reservation.getCheckIn(),
                        reservation.getCheckOut());


        double currentPrice =
                currentRoom.getBasePrice();
    %>


    <!-- ========================= -->
    <!-- CURRENT ROOM -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>Current Room</h2>

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
                            currentPrice) %>
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
    <!-- AVAILABLE UPGRADES -->
    <!-- ========================= -->

    <div class="reservation-card">

        <h2>Available Upgrade Rooms</h2>

        <%
        if (upgradeRooms == null ||
            upgradeRooms.isEmpty()) {
        %>

            <div class="cancelled-message">

                <h3>No Upgrade Rooms Available</h3>

                <p>
                    There are currently no higher-priced
                    rooms available for your selected dates.
                </p>

            </div>

        <%
        } else {
        %>


            <%
            for (Room room : upgradeRooms) {

                double priceDifference =
                        room.getBasePrice() -
                        currentPrice;

                double additionalAmount =
                        priceDifference *
                        numberOfNights;
            %>


            <div class="reservation-card">

                <h3>
                    Room <%= room.getRoomNumber() %>
                </h3>


                <div class="details-grid">

                    <div class="detail-item">

                        <span>Room Type</span>

                        <strong>
                            <%= room.getTypeName() %>
                        </strong>

                    </div>


                    <div class="detail-item">

                        <span>New Price / Night</span>

                        <strong>
                            Rs.
                            <%= String.format(
                                    "%,.0f",
                                    room.getBasePrice()) %>
                        </strong>

                    </div>


                    <div class="detail-item">

                        <span>Difference / Night</span>

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


                <br>


                <!-- Upgrade button -->
                <form
                    action="UpgradePaymentServlet"
                    method="get"
                >

                    <input
                        type="hidden"
                        name="reservationId"
                        value="<%= reservation.getReservationId() %>"
                    >


                    <input
                        type="hidden"
                        name="newRoomId"
                        value="<%= room.getRoomId() %>"
                    >


                    <button
                        type="submit"
                        class="btn"
                    >

                        🔄 Upgrade to this Room

                    </button>

                </form>

            </div>


            <%
            }
            %>


        <%
        }
        %>

    </div>


    <br>


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
