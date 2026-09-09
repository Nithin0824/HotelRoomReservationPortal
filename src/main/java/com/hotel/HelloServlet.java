package com.hotel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.hotel.model.Room;
import com.hotel.service.RoomService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        RoomService roomService = new RoomService();

        // Get search details from the form
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String guests = request.getParameter("guests");

        // Server-side validation
        if (checkIn == null || checkIn.trim().isEmpty() ||
            checkOut == null || checkOut.trim().isEmpty() ||
            guests == null || guests.trim().isEmpty()) {

            showError(response,
                    "Please enter check-in date, check-out date, and number of guests.");
            return;
        }

        int guestCount;

        try {

            guestCount = Integer.parseInt(guests);

        } catch (NumberFormatException e) {

            showError(response,
                    "Number of guests must be a valid number.");
            return;
        }

        if (guestCount < 1  ) {

            showError(response,
                    "Number of guests must be at least 1.");
            return;
        }
        
        if (guestCount > 5  ) {

            showError(response,
                    "Number of guests must be less than 5 .");
            return;
        }
        LocalDate checkInDate;
        LocalDate checkOutDate;

        try {

            checkInDate = LocalDate.parse(checkIn);
            checkOutDate = LocalDate.parse(checkOut);

        } catch (DateTimeParseException e) {

            showError(response,
                    "Please enter valid check-in and check-out dates.");
            return;
        }

        if (!checkInDate.isBefore(checkOutDate)) {

            showError(response,
                    "Check-out date must be after check-in date.");
            return;
        }

        if (checkInDate.isBefore(LocalDate.now())) {

            showError(response,
                    "Check-in date cannot be in the past.");
            return;
        }

        // Store search details
        request.setAttribute("checkIn", checkIn);
        request.setAttribute("checkOut", checkOut);
        request.setAttribute("guests", guests);

        // Get available rooms through the Service layer
        List<Room> rooms =
                roomService.findAvailableRooms(checkIn, checkOut);

        // Send room data to rooms.jsp
        request.setAttribute("rooms", rooms);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("rooms.jsp");

        dispatcher.forward(request, response);
    }

    private void showError(HttpServletResponse response,
                           String message)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Invalid Search</title>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println(
                "<h1>Invalid Search</h1>");

        response.getWriter().println(
                "<p>" + message + "</p>");

        response.getWriter().println(
                "<p>Please go back and enter valid search details.</p>");

        response.getWriter().println(
                "<br><a href=\"welcome.jsp\">" +
                "Back to Hotel Reservation Portal" +
                "</a>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}