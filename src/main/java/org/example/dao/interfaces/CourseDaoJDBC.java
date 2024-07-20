package org.example.dao.interfaces;

import org.example.model.Course;

import java.util.List;

public interface CourseDaoJDBC {
    void saveCourse(String name, double cost);

    Course getCourse(int id);

    List<Course> getAllCourses();

    void deleteCourse(int id);

    void setCurator(int user_id, int id);

    int getCurator(int id);

    List<Integer> getUserList(int course_id);
}
