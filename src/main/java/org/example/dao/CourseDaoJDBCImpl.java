package org.example.dao;

import org.example.dao.interfaces.CourseDaoJDBC;
import org.example.model.Course;
import org.example.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoJDBCImpl implements CourseDaoJDBC {
    Connection connection = DBConnection.getDBConnection();

    public CourseDaoJDBCImpl() throws SQLException {
    }

    /**
     * saving new course in DB
     *
     * @param name
     * @param cost
     */
    @Override
    public void saveCourse(String name, double cost) {
        String query = "INSERT INTO courses(name, cost) VALUES(?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, cost);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting the course by id
     *
     * @param id
     * @return
     */
    @Override
    public Course getCourse(int id) {
        String query = "SELECT id, name, cost FROM courses WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setCost(rs.getDouble("cost"));
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Course with id " + id + " not found");
        return null;
    }

    /**
     * getting a list of courses
     *
     * @return
     */
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String query = "SELECT * FROM courses";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Course course = new Course();

                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setCost(rs.getDouble("cost"));
                courses.add(course);
                System.out.println(course.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * deleting the course by id
     *
     * @param id
     */
    @Override
    public void deleteCourse(int id) {
        String query = "DELETE FROM courses WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * setting a curator in OneToMany schema
     *
     * @param user_id
     * @param id
     */
    @Override
    public void setCurator(int user_id, int id) {
        String query = "UPDATE courses SET curator = (SELECT id FROM users WHERE id = ?) WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting the curator by id
     *
     * @param id
     * @return
     */
    @Override
    public int getCurator(int id) {
        String query = "SELECT curator FROM courses WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int curator = rs.getInt("curator");
                if (curator != 0) {
                    System.out.println("Course with id " + id + " has a curator with id " + curator);
                    return curator;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Course with id " + id + " has no curator");
        return 0;
    }

    /**
     * getting a list of users enlisted the course
     *
     * @param course_id
     * @return
     */
    @Override
    public List<Integer> getUserList(int course_id) {
        List<Integer> userList = new ArrayList<>();
        String query = "SELECT user_id FROM user_courses WHERE course_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(query)) {
            pstm.setInt(1, course_id);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                userList.add(user_id);
                System.out.println("Course with id " + course_id + " is visited by user " + user_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}