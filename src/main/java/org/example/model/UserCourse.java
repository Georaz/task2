package org.example.model;

public class UserCourse {

    private int userId;
    private int courseId;
    private int userCourseId;

    public UserCourse() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserCourseId() {
        return userCourseId;
    }

    public void setUserCourseId(int userCourseId) {
        this.userCourseId = userCourseId;
    }
}
