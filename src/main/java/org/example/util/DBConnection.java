package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "db_url";
    private static final String USERNAME = "db_username";
    private static final String PASSWORD = "db_password";

    private static Connection connection;

    /**
     * getting connection to DB, creating and droping tables
     * @return
     * @throws SQLException
     */
    public static Connection getDBConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    PropertiesUtil.get(URL),
                    PropertiesUtil.get(USERNAME),
                    PropertiesUtil.get(PASSWORD)
            );
        } catch (SQLException e) {
            System.out.println("Ошибка соединения с БД.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void createUsersTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String table = """             
                    CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100),
                    age INTEGER 
                    );                
                    """;
            stmt.executeUpdate(table);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы readers");
            e.printStackTrace();
        }
    }

    public static void dropUsersTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String drop = "DROP TABLE IF EXISTS users CASCADE";
            stmt.executeUpdate(drop);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы readers");
            e.printStackTrace();
        }
    }

    public static void createCoursesTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String table = """
                    CREATE TABLE IF NOT EXISTS courses (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100),
                        cost DOUBLE PRECISION,
                        curator INTEGER,
                        FOREIGN KEY (curator) REFERENCES users(id) ON DELETE SET NULL
                    );                    
                    """;
            stmt.executeUpdate(table);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы courses");
            e.printStackTrace();
        }
    }

    public static void dropCoursesTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String drop = "DROP TABLE IF EXISTS courses CASCADE";
            stmt.executeUpdate(drop);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы courses");
            e.printStackTrace();
        }
    }

    public static void createUserCoursesTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String table = """
                    CREATE TABLE IF NOT EXISTS user_courses (
                        user_id INTEGER,
                        course_id INTEGER,
                        user_course_id SERIAL PRIMARY KEY,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (course_id) REFERENCES courses(id)
                    );                    
                    """;
            stmt.executeUpdate(table);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы user_courses");
            e.printStackTrace();
        }
    }

    public static void dropUserCoursesTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String drop = "DROP TABLE IF EXISTS user_courses CASCADE";
            stmt.executeUpdate(drop);
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы user_courses");
            e.printStackTrace();
        }
    }

    public static void createTables() throws SQLException {
        try {
            createUsersTable();
            createCoursesTable();
            createUserCoursesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTables() throws SQLException {
        try {
            dropUsersTable();
            dropCoursesTable();
            dropUserCoursesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}