package org.example.dao;

import org.example.dao.interfaces.UserDaoJDBC;
import org.example.model.User;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDaoJDBC {
    Connection connection = DBConnection.getDBConnection();

    public UserDaoJDBCImpl() throws SQLException {
    }

    /**
     * saving new user
     * @param name
     * @param age
     */
    @Override
    public void saveUser(String name, int age) {
        String query = "INSERT INTO users(name, age) VALUES(?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting a user by id
     * @param id
     * @return
     */
    @Override
    public User getUser(int id) {
        String query = "SELECT name, age FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println("User with id " + id + " is " + name + " with age " + age);
                return new User(name, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User with id " + id + " not found");
        return null;
    }

    /**
     * getting a list of users
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                users.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * deleting a user by id
     * @param id
     */
    @Override
    public void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting a list of courses user enlisted
     * @param user_id
     * @return
     */
    @Override
    public List<Integer> getCourseList(int user_id) {
        List<Integer> courseList = new ArrayList<>();
        String query = "SELECT course_id FROM user_courses WHERE user_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setInt(1, user_id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                courseList.add(course_id);
                System.out.println("User with id " + user_id + " joint to course with id " + course_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }
}