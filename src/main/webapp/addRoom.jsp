<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>

<!DOCTYPE html> <html> <head> <meta charset="UTF-8"> <title>Admin - Add New Room</title> <link rel="stylesheet" href="css/style.css"> </head> <body> <div class="container">
<div class="page-header">

    <h1>➕ Add New Room</h1>

    <p>Add a new room to the hotel inventory</p>

</div>


<div class="reservation-card">

    <form action="AdminRoomsServlet"
          method="post">

        <input type="hidden"
               name="action"
               value="add">


        <label for="roomNumber">
            Room Number
        </label>

        <input type="number"
               id="roomNumber"
               name="roomNumber"
               min="1"
               required>


        <br><br>


        <label for="typeId">
            Room Type
        </label>

        <select id="typeId"
                name="typeId"
                required>

            <option value="">
                -- Select Room Type --
            </option>

            <option value="1">
                Deluxe Room - Rs. 4,000
            </option>

            <option value="2">
                Suite - Rs. 7,000
            </option>

            <option value="21">
                Presidential Suite - Rs. 15,000
            </option>

        </select>


        <br><br>


        <label for="status">
            Status
        </label>

        <select id="status"
                name="status"
                required>

            <option value="AVAILABLE">
                AVAILABLE
            </option>

            <option value="UNAVAILABLE">
                UNAVAILABLE
            </option>

        </select>


        <br><br>


        <button type="submit">
            ➕ Add Room
        </button>

    </form>


    <br>


    <a href="AdminRoomsServlet"
       class="back-link">

        ← Back to Room Inventory

    </a>

</div>

</div> <div class="footer">
<p>
    © 2026 Hotel Reservation Portal
</p>

</div> </body> </html>