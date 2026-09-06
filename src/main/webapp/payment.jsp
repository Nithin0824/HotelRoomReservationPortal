<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Payment</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="hotel-header">

    <h1>💳 Payment</h1>

    <p>Complete your payment to confirm your booking.</p>

</div>


<div class="container">

    <div class="confirmation-card">

        <h2>Payment Details</h2>

        <hr>

        <div class="details-grid">

            <div class="detail-item">

                <span>Reservation ID</span>

                <strong>
                    <%= request.getAttribute("reservationId") %>
                </strong>

            </div>


            <div class="total-item">

                <span>Total Amount</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            Double.parseDouble(
                                    String.valueOf(
                                            request.getAttribute("amount")
                                    )
                            )
                    ) %>
                </strong>

            </div>

        </div>

        <hr>


        <form action="PaymentServlet" method="post">

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


            <div class="detail">

                <label for="paymentMethod">
                    <strong>Payment Method</strong>
                </label>

                <select
                    id="paymentMethod"
                    name="paymentMethod"
                    required
                >

                    <option value="">
                        -- Select Payment Method --
                    </option>

                    <option value="UPI">
                        UPI
                    </option>

                    <option value="CARD">
                        Credit / Debit Card
                    </option>

                    <option value="NET_BANKING">
                        Net Banking
                    </option>

                </select>

            </div>


            <br>


            <button
                type="submit"
                class="btn"
            >
                Pay Now
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

</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body>

</html>
