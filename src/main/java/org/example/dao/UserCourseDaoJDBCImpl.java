package org.example.dao;

import org.example.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCourseDaoJDBCImpl {
    Connection connection = DBConnection.getDBConnection();

    public UserCourseDaoJDBCImpl() throws SQLException {
    }

    /**
     * getting a pair of user_id and course_id in ManyToMany schema
     * @param user_id
     * @param course_id
     * @throws SQLException
     */
    public void addUserCourse(int user_id, int course_id) throws SQLException {
        String query = "INSERT INTO user_courses (user_id, course_id) VALUES (?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setInt(1, user_id);
            pstm.setInt(2, course_id);
            pstm.executeUpdate();
            System.out.println("User with id " + user_id + " is enlisted course with id " + course_id);
        } catch (SQLException e) {
            System.out.println("Enlisting error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
