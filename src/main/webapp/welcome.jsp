<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head>
<meta charset="UTF-8">

<title>Hotel Reservation Portal</title>

<style>

    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
        font-family: Arial, Helvetica, sans-serif;
        background: #f4f6f8;
        color: #333;
    }

    .header {
        background: #1f3c88;
        color: white;
        padding: 25px 50px;
        text-align: center;
    }

    .header h1 {
        margin: 0;
        font-size: 32px;
    }

    .header p {
        margin: 8px 0 0;
        font-size: 16px;
    }

    .container {
        width: 90%;
        max-width: 900px;
        margin: 40px auto;
    }

    .card {
        background: white;
        padding: 30px;
        margin-bottom: 25px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
    }

    .card h2 {
        margin-top: 0;
        color: #1f3c88;
    }

    .form-group {
        margin-bottom: 20px;
    }

    label {
        display: block;
        font-weight: bold;
        margin-bottom: 8px;
    }

    input {
        width: 100%;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
    }

    input:focus {
        border-color: #1f3c88;
        outline: none;
    }

    .search-button {
        width: 100%;
        padding: 13px;
        background: #1f3c88;
        color: white;
        border: none;
        border-radius: 6px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
    }

    .search-button:hover {
        background: #162d68;
    }

    .reservation-section {
        text-align: center;
    }

    .reservation-section p {
        color: #666;
    }

    .view-button {
        display: inline-block;
        padding: 12px 25px;
        background: #198754;
        color: white;
        text-decoration: none;
        border-radius: 6px;
        font-weight: bold;
    }

    .view-button:hover {
        background: #146c43;
    }

    .footer {
        text-align: center;
        padding: 20px;
        color: #777;
        font-size: 14px;
    }

</style>

</head> <body>
<div class="header">

    <h1>🏨 Hotel Reservation Portal</h1>

    <p>Find your perfect room and enjoy a comfortable stay.</p>

</div>


<div class="container">


    <!-- Search Rooms -->

    <div class="card">

        <h2>🔎 Search Available Rooms</h2>

        <p>
            Enter your stay details to find available rooms.
        </p>


        <form action="HelloServlet" method="get">


            <div class="form-group">

                <label for="checkIn">
                    Check-in
                </label>

                <input
                    type="date"
                    id="checkIn"
                    name="checkIn"
                    required>

            </div>


            <div class="form-group">

                <label for="checkOut">
                    Check-out
                </label>

                <input
                    type="date"
                    id="checkOut"
                    name="checkOut"
                    required>

            </div>


            <div class="form-group">

                <label for="guests">
                    Number of Guests
                </label>

                <input
                    type="number"
                    id="guests"
                    name="guests"
                    min="1"
                    required>

            </div>


            <button
                type="submit"
                class="search-button">

                Search Rooms

            </button>


        </form>

    </div>


    <!-- Existing Reservation -->

    <div class="card reservation-section">

        <h2>📋 Already Have a Reservation?</h2>

        <p>
            Enter your Reservation ID to view
            or manage your booking.
        </p>


        <a
            href="viewReservationForm.jsp"
            class="view-button">

            View My Reservation

        </a>
        <br><br>

<a
    href="adminLogin.jsp"
    class="view-button">

    🔐 Admin Login

</a>
        

    </div>


</div>


<div class="footer">

    <p>
        © 2026 Hotel Reservation Portal
    </p>

</div>

</body> </html>