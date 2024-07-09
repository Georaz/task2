package org.example.service;

import org.example.dao.UserDaoJDBCImpl;
import org.example.dto.UserDto;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDaoJDBCImpl userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {
        UserDto userDto = new UserDto();
        userDto.setName("Test User");
        userDto.setAge(25);

        userService.saveUser(userDto);

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> ageCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userDao).saveUser(nameCaptor.capture(), ageCaptor.capture());

        assertEquals("Test User", nameCaptor.getValue());
        assertEquals(25, ageCaptor.getValue());
    }

    @Test
    void getUser() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setAge(25);

        when(userDao.getUser(1)).thenReturn(user);

        UserDto userDto = userService.getUser(1);

        assertNotNull(userDto);
        assertEquals(1, userDto.getId());
        assertEquals("Test User", userDto.getName());
        assertEquals(25, userDto.getAge());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("User 1");
        user1.setAge(25);
        users.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setName("User 2");
        user2.setAge(30);
        users.add(user2);

        when(userDao.getAllUsers()).thenReturn(users);

        List<UserDto> userDtos = userService.getAllUsers();

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals(1, userDtos.get(0).getId());
        assertEquals("User 1", userDtos.get(0).getName());
        assertEquals(25, userDtos.get(0).getAge());
        assertEquals(2, userDtos.get(1).getId());
        assertEquals("User 2", userDtos.get(1).getName());
        assertEquals(30, userDtos.get(1).getAge());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1);

        verify(userDao).deleteUser(1);
    }

    @Test
    void getCourseList() throws SQLException {
        List<Integer> courseList = new ArrayList<>();
        courseList.add(1);
        courseList.add(2);

        when(userDao.getCourseList(1)).thenReturn(courseList);

        List<Integer> result = userService.getCourseList(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).intValue());
        assertEquals(2, result.get(1).intValue());
    }
}