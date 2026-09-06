<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Payment Successful</title>

<link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="hotel-header">

    <h1>💳 Payment Successful</h1>

    <p>Your payment has been processed successfully.</p>

</div>


<div class="container">

    <div class="confirmation-card">

        <div class="confirmation-icon">
            ✓
        </div>

        <h1>Payment Successful!</h1>

        <p class="confirmation-text">
            Thank you for your payment.
        </p>

        <p>
            Your hotel reservation payment has been recorded.
        </p>


        <hr>


        <h2>Payment Details</h2>


        <div class="details-grid">


            <div class="detail-item">

                <span>Payment ID</span>

                <strong>
                    <%= request.getAttribute("paymentId") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Reservation ID</span>

                <strong>
                    <%= request.getAttribute("reservationId") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Payment Method</span>

                <strong>
                    <%= request.getAttribute("paymentMethod") %>
                </strong>

            </div>


            <div class="detail-item">

                <span>Payment Status</span>

                <strong>
                    <%= request.getAttribute("paymentStatus") %>
                </strong>

            </div>


            <div class="total-item">

                <span>Amount Paid</span>

                <strong>
                    Rs.
                    <%= String.format(
                            "%,.0f",
                            (Double) request.getAttribute("amount")
                    ) %>
                </strong>

            </div>


        </div>


        <hr>


        <div class="confirmation-actions">

            <a
                href="viewReservationForm.jsp"
                class="btn"
            >
                🔎 View My Reservation
            </a>


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
