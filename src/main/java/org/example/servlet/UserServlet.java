package org.example.servlet;

import org.example.dto.UserDto;
import org.example.service.UserService;
import org.example.dao.UserDaoJDBCImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            this.userService = new UserService(new UserDaoJDBCImpl());
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize UserService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (idParam == null) {
            List<UserDto> users = userService.getAllUsers();
            String jsonResponse = new Gson().toJson(users);
            response.getWriter().write(jsonResponse);
        } else {
            try {
                int id = Integer.parseInt(idParam);
                UserDto user = userService.getUser(id);
                if (user != null) {
                    String jsonResponse = new Gson().toJson(user);
                    response.getWriter().write(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"User not found\"}");
                }
            } catch (NumberFormatException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid user ID\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDto userDto = new Gson().fromJson(request.getReader(), UserDto.class);
            userService.saveUser(userDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid user data\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                userService.deleteUser(id);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid user ID\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"User ID is required\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("id");
        String courseIdParam = request.getParameter("courseId");

        if (userIdParam != null && courseIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                int courseId = Integer.parseInt(courseIdParam);
                List<Integer> courses = userService.getCourseList(userId);
                if (courses != null && courses.contains(courseId)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"User not enrolled in the course\"}");
                }
            } catch (NumberFormatException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid IDs\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"User ID and Course ID are required\"}");
        }
    }
}