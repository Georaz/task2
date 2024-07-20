package org.example.service;

import org.example.dao.UserCourseDaoJDBCImpl;
import org.example.dto.UserCourseDto;

import java.sql.SQLException;

public class UserCourseService {

    private final UserCourseDaoJDBCImpl userCourseDao;

    public UserCourseService() throws SQLException {
        this.userCourseDao = new UserCourseDaoJDBCImpl();
    }

    /**
     * linking a user with course
     * @param userCourseDto
     */
    public void addUserToCourse(UserCourseDto userCourseDto) {
        try {
            userCourseDao.addUserCourse(userCourseDto.getUserId(), userCourseDto.getCourseId());
        } catch (Exception e) {
            System.err.println("Error adding user to course: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

