package org.example.service;

import org.example.dao.CourseDaoJDBCImpl;
import org.example.dto.CourseDto;
import org.example.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseDaoJDBCImpl courseDao;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourse() {
        CourseDto courseDto = new CourseDto();
        courseDto.setName("Test Course");
        courseDto.setCost(100.0);

        courseService.saveCourse(courseDto);

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> costCaptor = ArgumentCaptor.forClass(Double.class);
        verify(courseDao).saveCourse(nameCaptor.capture(), costCaptor.capture());

        assertEquals("Test Course", nameCaptor.getValue());
        assertEquals(100.0, costCaptor.getValue());
    }

    @Test
    void getCourse() throws SQLException {
        Course course = new Course();
        course.setId(1);
        course.setName("Test Course");
        course.setCost(100.0);

        when(courseDao.getCourse(1)).thenReturn(course);

        CourseDto courseDto = courseService.getCourse(1);

        assertNotNull(courseDto);
        assertEquals(1, courseDto.getId());
        assertEquals("Test Course", courseDto.getName());
        assertEquals(100.0, courseDto.getCost());
    }

    @Test
    void getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Course 1");
        course1.setCost(100.0);
        courses.add(course1);

        Course course2 = new Course();
        course2.setId(2);
        course2.setName("Course 2");
        course2.setCost(200.0);
        courses.add(course2);

        when(courseDao.getAllCourses()).thenReturn(courses);

        List<CourseDto> courseDtos = courseService.getAllCourses();

        assertNotNull(courseDtos);
        assertEquals(2, courseDtos.size());
        assertEquals(1, courseDtos.get(0).getId());
        assertEquals("Course 1", courseDtos.get(0).getName());
        assertEquals(100.0, courseDtos.get(0).getCost());
        assertEquals(2, courseDtos.get(1).getId());
        assertEquals("Course 2", courseDtos.get(1).getName());
        assertEquals(200.0, courseDtos.get(1).getCost());
    }

    @Test
    void deleteCourse() {
        courseService.deleteCourse(1);

        verify(courseDao).deleteCourse(1);
    }

    @Test
    void setCurator() throws SQLException {
        courseService.setCurator(1, 1);

        verify(courseDao).setCurator(1, 1);
    }

    @Test
    void getCurator() {
        when(courseDao.getCurator(1)).thenReturn(1);

        int curatorId = courseService.getCurator(1);

        assertEquals(1, curatorId);
    }

    @Test
    void getUserList() throws SQLException {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);

        when(courseDao.getUserList(1)).thenReturn(userIds);

        List<Integer> result = courseService.getUserList(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).intValue());
        assertEquals(2, result.get(1).intValue());
    }
}