package org.example.dao.interfaces;

import org.example.model.User;

import java.util.List;

public interface UserDaoJDBC {

    void saveUser(String name, int age);

    User getUser(int id);

    List<User> getAllUsers();

    void deleteUser(int id);

    List<Integer> getCourseList(int user_id);
}