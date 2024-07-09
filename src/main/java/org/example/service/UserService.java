package org.example.service;

import org.example.dao.UserDaoJDBCImpl;
import org.example.dto.UserDto;
import org.example.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserDaoJDBCImpl userDao;

    public UserService(UserDaoJDBCImpl userDao) {
        this.userDao = userDao;
    }

    /**
     * converting a model into DTO
     * @param user
     * @return
     */
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        return userDto;
    }

    /**
     * converting a DTO into model
     * @param userDto
     * @return
     */
    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        return user;
    }

    /**
     * saving a user
     * @param userDto
     */
    public void saveUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.saveUser(user.getName(), user.getAge());
    }

    /**
     * getting a user
     * @param id
     * @return
     * @throws SQLException
     */
    public UserDto getUser(int id) throws SQLException {
        User user = userDao.getUser(id);
        return convertToDto(user);
    }

    /**
     * getting a list of users
     * @return
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(convertToDto(user));
        }

        return userDtos;
    }

    /**
     * deleting a user
     * @param id
     */
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    /**
     * getting a list of courses user enlisted
     * @param userId
     * @return
     * @throws SQLException
     */
    public List<Integer> getCourseList(int userId) throws SQLException {
        return userDao.getCourseList(userId);
    }
}
