package org.example;

import java.sql.Connection;
import java.sql.SQLException;

import org.example.dao.CourseDaoJDBCImpl;
import org.example.dao.UserCourseDaoJDBCImpl;
import org.example.dao.UserDaoJDBCImpl;
import org.example.model.User;
import org.example.model.UserCourse;
import org.example.util.DBConnection;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection.getDBConnection();
        DBConnection.dropTables();
        DBConnection.createTables();

        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        userDao.saveUser("Fipol", 23);
        userDao.saveUser("Phil", 35);
        userDao.saveUser("Dafna", 19);
        userDao.saveUser("Zas", 34);
        userDao.saveUser("Arty", 18);
        userDao.saveUser("Ernie", 45);

        userDao.getUser(2);
        userDao.getUser(3);
        userDao.getUser(4);

        userDao.getAllUsers();

        userDao.deleteUser(2);

        CourseDaoJDBCImpl courseDao = new CourseDaoJDBCImpl();

        courseDao.saveCourse("Java Programming", 12.5);
        courseDao.saveCourse("Python Programming", 7);
        courseDao.saveCourse("Scala Programming", 19.5);

        courseDao.getCourse(2);
        courseDao.getCourse(3);
        courseDao.getCourse(4);

        courseDao.getAllCourses();

        courseDao.deleteCourse(2);

        courseDao.getCurator(3);

        courseDao.setCurator(2, 2);
        courseDao.getCurator(2);
        userDao.deleteUser(2);
        UserCourseDaoJDBCImpl userCourseDao = new UserCourseDaoJDBCImpl();
        userCourseDao.addUserCourse(1, 2);
        userCourseDao.addUserCourse(2, 2);
        userCourseDao.addUserCourse(3, 2);
        userCourseDao.addUserCourse(3, 1);
        userCourseDao.addUserCourse(4, 1);
        userCourseDao.addUserCourse(4, 2);

        userDao.getCourseList(3);
        courseDao.getUserList(2);
    }
}