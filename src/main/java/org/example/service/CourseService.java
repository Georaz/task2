package org.example.service;

import org.example.dao.CourseDaoJDBCImpl;
import org.example.dto.CourseDto;
import org.example.model.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private CourseDaoJDBCImpl courseDao;

    public CourseService() throws SQLException {
        this.courseDao = new CourseDaoJDBCImpl();
    }

    /**
     * converting a model into DTO
     * @param course
     * @return
     */
    private CourseDto convertToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setCost(course.getCost());
        return courseDto;
    }

    /**
     * converting a DTO into model
     * @param courseDto
     * @return
     */
    private Course convertToEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setId(courseDto.getId());
        course.setName(courseDto.getName());
        course.setCost(courseDto.getCost());
        return course;
    }

    /**
     * saving a course
     * @param courseDto
     */
    public void saveCourse(CourseDto courseDto) {
        courseDao.saveCourse(courseDto.getName(), courseDto.getCost());
    }

    /**
     * getting a course
     * @param id
     * @return
     * @throws SQLException
     */
    public CourseDto getCourse(int id) throws SQLException {
        Course course = courseDao.getCourse(id);
        return convertToDto(course);
    }

    /**
     * getting a list of users
     * @return
     */
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseDao.getAllCourses();
        List<CourseDto> courseDtos = new ArrayList<>();

        for (Course course : courses) {
            courseDtos.add(convertToDto(course));
        }

        return courseDtos;
    }

    /**
     * deleting a course
     * @param id
     */
    public void deleteCourse(int id) {
        courseDao.deleteCourse(id);
    }

    /**
     * setting a curator
     * @param userId
     * @param courseId
     * @throws SQLException
     */
    public void setCurator(int userId, int courseId) throws SQLException {
        courseDao.setCurator(userId, courseId);
    }

    /**
     * getting a curator
     * @param courseId
     * @return
     */
    public int getCurator(int courseId) {
        return courseDao.getCurator(courseId);
    }

    /**
     * getting a list of users enlisted the course
     * @param courseId
     * @return
     * @throws SQLException
     */
    public List<Integer> getUserList(int courseId) throws SQLException {
        return courseDao.getUserList(courseId);
    }
}