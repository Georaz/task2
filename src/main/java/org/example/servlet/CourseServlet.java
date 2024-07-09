package org.example.servlet;

import org.example.dto.CourseDto;
import org.example.service.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    CourseService courseService;

    @Override
    public void init() throws ServletException {
        try {
            this.courseService = new CourseService();
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize CourseService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (idParam == null) {
            List<CourseDto> courses = courseService.getAllCourses();
            String jsonResponse = new Gson().toJson(courses);
            response.getWriter().write(jsonResponse);
        } else {
            try {
                int id = Integer.parseInt(idParam);
                CourseDto course = courseService.getCourse(id);
                if (course != null) {
                    String jsonResponse = new Gson().toJson(course);
                    response.getWriter().write(jsonResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"message\": \"Course not found\"}");
                }
            } catch (NumberFormatException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid course ID\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CourseDto courseDto = new Gson().fromJson(request.getReader(), CourseDto.class);
            courseService.saveCourse(courseDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid course data\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                courseService.deleteCourse(id);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid course ID\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Course ID is required\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String curatorIdParam = request.getParameter("curatorId");

        if (idParam != null && curatorIdParam != null) {
            try {
                int courseId = Integer.parseInt(idParam);
                int curatorId = Integer.parseInt(curatorIdParam);
                courseService.setCurator(curatorId, courseId);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NumberFormatException | SQLException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid IDs\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Course ID and Curator ID are required\"}");
        }
    }
}

