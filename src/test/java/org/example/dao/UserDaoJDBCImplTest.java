package org.example.dao;

import org.example.model.User;
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
public class UserDaoJDBCImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDaoJDBCImpl userDao;
    private Connection connection;

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void init() throws SQLException {
        connection = DBConnection.getDBConnection();
        userDao = new UserDaoJDBCImpl();
        DBConnection.createUsersTable();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBConnection.dropUsersTable();
    }

    @AfterAll
    public static void close() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testSaveUser() throws SQLException {
        userDao.saveUser("Test User", 25);
        User user = userDao.getUser(1);
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals(25, user.getAge());
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        userDao.saveUser("User 1", 30);
        userDao.saveUser("User 2", 35);
        List<User> users = userDao.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        userDao.saveUser("User to Delete", 40);
        userDao.deleteUser(1);
        User user = userDao.getUser(1);
        assertNull(user);
    }

    @Test
    public void testGetCourseList() throws SQLException {
        // Create necessary tables and insert data
        try (var statement = connection.createStatement()) {
            DBConnection.createCoursesTable();
            DBConnection.createUserCoursesTable();
            statement.execute("INSERT INTO courses (name, cost) VALUES ('Course 1', 100.0)");
            userDao.saveUser("Test User", 25);
            statement.execute("INSERT INTO user_courses (user_id, course_id) VALUES (1, 1)");
        }

        List<Integer> courses = userDao.getCourseList(1);
        assertEquals(1, courses.size());
        assertEquals(1, courses.get(0));
    }
}