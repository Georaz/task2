package org.example.dto;

public class CourseDto {
    private int id;
    private String name;
    private double cost;
    private int curator;

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
}
