<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head> <meta charset="UTF-8"> <title>Admin Login</title> <link rel="stylesheet" href="css/style.css"> </head> <body> <div class="container">
<div class="page-header">

    <h1>🔐 Admin Login</h1>

    <p>Hotel Reservation Portal Administration</p>

</div>

<div class="reservation-card">

    <form action="AdminLoginServlet" method="post">

        <div class="form-group">

            <label for="username">Username</label>

            <input
                type="text"
                id="username"
                name="username"
                required
            >

        </div>

        <br>

        <div class="form-group">

            <label for="password">Password</label>

            <input
                type="password"
                id="password"
                name="password"
                required
            >

        </div>

        <br>

        <button type="submit" class="btn">
            🔐 Login
        </button>

    </form>

    <br>

    <a href="welcome.jsp" class="back-link">
        ← Back to Hotel Reservation Portal
    </a>

</div>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>