package org.example.model;

import java.util.List;

public class Course {
    private int id;
    private String name;
    private double cost;
    private int curator;
    private List<Integer> userList;

    public Course() {
    }

    public Course(String name, double cost) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCurator() {
        return curator;
    }

    public void setCurator(int curator) {
        this.curator = curator;
    }

    public List<Integer> getUserList() {
        return userList;
    }

    public void setUserList(List<Integer> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return String.format("Course with id '%s': name = '%s', cost = '%s'", getId(), getName(), getCost());
    }
}
