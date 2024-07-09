package org.example.dao;

import org.example.model.Course;
import org.example.util.DBConnection;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class CourseDaoJDBCImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private CourseDaoJDBCImpl courseDao;
    private Connection connection;

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void init() throws SQLException {
        connection = DBConnection.getDBConnection();
        courseDao = new CourseDaoJDBCImpl();
        DBConnection.createUsersTable();
        DBConnection.createCoursesTable();
        DBConnection.createUserCoursesTable();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBConnection.dropTables();
    }

    @AfterAll
    public static void close() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testSaveCourse() throws SQLException {
        courseDao.saveCourse("Test Course", 99.99);
        Course course = courseDao.getCourse(1);
        assertNotNull(course);
        assertEquals("Test Course", course.getName());
        assertEquals(99.99, course.getCost());
    }

    @Test
    public void testGetAllCourses() {
        courseDao.saveCourse("Course 1", 50.0);
        courseDao.saveCourse("Course 2", 100.0);
        List<Course> courses = courseDao.getAllCourses();
        assertEquals(2, courses.size());
    }

    @Test
    public void testDeleteCourse() throws SQLException {
        courseDao.saveCourse("Course to Delete", 10.0);
        courseDao.deleteCourse(1);
        Course course = courseDao.getCourse(1);
        assertNull(course);
    }

    @Test
    public void testSetCurator() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("INSERT INTO users (name, age) VALUES ('Curator', 40);");
        }
        courseDao.saveCourse("Course with Curator", 20.0);
        courseDao.setCurator(1, 1);
        int curatorId = courseDao.getCurator(1);
        assertEquals(1, curatorId);
    }
}