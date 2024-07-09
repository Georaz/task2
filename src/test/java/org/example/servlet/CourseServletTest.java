package org.example.servlet;

import org.example.dto.CourseDto;
import org.example.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServletTest {

    private CourseServlet courseServlet;

    @Mock
    private CourseService courseService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletConfig servletConfig;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        courseServlet = new CourseServlet();
        courseServlet.init(servletConfig);
        courseServlet.courseService = courseService; // Установим мок CourseService напрямую
    }

    @Test
    void testDoGetWithoutIdParam() throws Exception {
        // Arrange
        List<CourseDto> courses = new ArrayList<>();
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setName("Course 1");
        courseDto.setCost(99.99);
        courseDto.setCurator(1);
        courses.add(courseDto);

        when(courseService.getAllCourses()).thenReturn(courses);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        courseServlet.doGet(request, response);

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertEquals(new Gson().toJson(courses), jsonResponse);
    }

    @Test
    void testDoGetWithIdParam() throws Exception {
        // Arrange
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setName("Course 1");
        courseDto.setCost(99.99);
        courseDto.setCurator(1);

        when(request.getParameter("id")).thenReturn("1");
        when(courseService.getCourse(1)).thenReturn(courseDto);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        courseServlet.doGet(request, response);

        // Assert
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertEquals(new Gson().toJson(courseDto), jsonResponse);
    }

    @Test
    void testDoPost() throws Exception {
        // Arrange
        CourseDto courseDto = new CourseDto();
        courseDto.setName("Course 1");
        courseDto.setCost(99.99);
        courseDto.setCurator(1);

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(courseDto))));

        // Act
        courseServlet.doPost(request, response);

        // Assert
        verify(courseService).saveCourse(any(CourseDto.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoDelete() throws Exception {
        // Arrange
        when(request.getParameter("id")).thenReturn("1");

        // Act
        courseServlet.doDelete(request, response);

        // Assert
        verify(courseService).deleteCourse(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoPut() throws Exception {
        // Arrange
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("curatorId")).thenReturn("2");

        // Act
        courseServlet.doPut(request, response);

        // Assert
        verify(courseService).setCurator(2, 1);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}