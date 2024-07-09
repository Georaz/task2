package org.example.servlet;

import org.example.dto.UserDto;
import org.example.service.UserService;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServletTest {

    private UserServlet userServlet;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletConfig servletConfig;

    @BeforeEach
    void setUp() throws ServletException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        userServlet = new UserServlet();
        userServlet.init(servletConfig);

        Field userServiceField = UserServlet.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userServlet, userService);
    }

    @Test
    void testDoGetWithoutIdParam() throws Exception {
        List<UserDto> users = new ArrayList<>();
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("User 1");
        users.add(userDto);

        when(userService.getAllUsers()).thenReturn(users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertEquals(new Gson().toJson(users), jsonResponse);
    }

    @Test
    void testDoGetWithIdParam() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("User 1");

        when(request.getParameter("id")).thenReturn("1");
        when(userService.getUser(1)).thenReturn(userDto);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        writer.flush();

        String jsonResponse = stringWriter.toString();
        assertEquals(new Gson().toJson(userDto), jsonResponse);
    }

    @Test
    void testDoPost() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("User 1");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(new Gson().toJson(userDto))));

        userServlet.doPost(request, response);

        verify(userService).saveUser(any(UserDto.class));
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void testDoDelete() throws Exception {
        when(request.getParameter("id")).thenReturn("1");

        userServlet.doDelete(request, response);

        verify(userService).deleteUser(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void testDoPut() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("courseId")).thenReturn("2");

        List<Integer> courses = new ArrayList<>();
        courses.add(2);

        when(userService.getCourseList(1)).thenReturn(courses);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        userServlet.doPut(request, response);

        verify(userService).getCourseList(1);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}