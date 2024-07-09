package org.example.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private List<Integer> courseList;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Integer> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Integer> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return String.format("User with id '%s': name = '%s', age = '%s'", getId(), getName(), getAge());
    }
}
