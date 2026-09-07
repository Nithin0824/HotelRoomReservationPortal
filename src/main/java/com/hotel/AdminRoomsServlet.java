package com.hotel;

import java.io.IOException;
import java.util.List;

import com.hotel.model.Room;
import com.hotel.service.RoomService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminRoomsServlet")
public class AdminRoomsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private RoomService roomService;

    @Override
    public void init() throws ServletException {
        roomService = new RoomService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        // Open Add Room page
        if ("add".equals(action)) {

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "addRoom.jsp");

            dispatcher.forward(
                    request,
                    response);

            return;
        }

        // Open Edit Room page
        if ("edit".equals(action)) {

            try {

                int roomId =
                        Integer.parseInt(
                                request.getParameter(
                                        "roomId"));

                Room room =
                        roomService.findRoomById(
                                roomId);

                if (room == null) {

                    throw new ServletException(
                            "Room not found.");
                }

                request.setAttribute(
                        "room",
                        room);

                RequestDispatcher dispatcher =
                        request.getRequestDispatcher(
                                "editRoom.jsp");

                dispatcher.forward(
                        request,
                        response);

            } catch (NumberFormatException e) {

                throw new ServletException(
                        "Invalid room ID.",
                        e);
            }

            return;
        }

        // Default: show room inventory
        loadRooms(request, response);
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        // Add new room
        if ("add".equals(action)) {

            try {

                int roomNumber =
                        Integer.parseInt(
                                request.getParameter(
                                        "roomNumber"));

                int typeId =
                        Integer.parseInt(
                                request.getParameter(
                                        "typeId"));

                String status =
                        request.getParameter(
                                "status");

                boolean added =
                        roomService.addRoom(
                                roomNumber,
                                typeId,
                                status);

                if (added) {

                    response.sendRedirect(
                            "AdminRoomsServlet");

                } else {

                    throw new ServletException(
                            "Room could not be added.");
                }

            } catch (NumberFormatException e) {

                throw new ServletException(
                        "Invalid room details.",
                        e);
            }

            return;
        }


        // Update existing room
        if ("edit".equals(action)) {

            try {

                int roomId =
                        Integer.parseInt(
                                request.getParameter(
                                        "roomId"));

                int roomNumber =
                        Integer.parseInt(
                                request.getParameter(
                                        "roomNumber"));

                int typeId =
                        Integer.parseInt(
                                request.getParameter(
                                        "typeId"));

                String status =
                        request.getParameter(
                                "status");

                boolean updated =
                        roomService.updateRoom(
                                roomId,
                                roomNumber,
                                typeId,
                                status);

                if (updated) {

                    response.sendRedirect(
                            "AdminRoomsServlet");

                } else {

                    throw new ServletException(
                            "Room could not be updated.");
                }

            } catch (NumberFormatException e) {

                throw new ServletException(
                        "Invalid room details.",
                        e);
            }

            return;
        }


        // Default behavior
        loadRooms(request, response);
    }


    private void loadRooms(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Room> rooms =
                    roomService.findAllRooms();

            request.setAttribute(
                    "rooms",
                    rooms);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher(
                            "adminRooms.jsp");

            dispatcher.forward(
                    request,
                    response);

        } catch (RuntimeException e) {

            throw new ServletException(
                    "Error retrieving room information.",
                    e);
        }
    }
}