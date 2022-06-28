package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.IUserService;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private UserController userController;

    @Test
    public void getAllUsers_returnUsers() throws UserException {
        // arrange
        User user1 = User.builder()
                .build();
        User user2 = User.builder()
                .build();

        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(user1);
        expectedUserList.add(user2);

        when(userServiceMock.findAll()).thenReturn(expectedUserList);

        ResponseEntity<?> response = userController.getAllUsers();

        List<User> actualUserList = (List<User>) userController.getAllUsers().getBody();

        verify(userServiceMock, atLeastOnce()).findAll();

        Assertions.assertEquals(2, actualUserList.size());
        Assertions.assertTrue(actualUserList.contains(user1));
        Assertions.assertTrue(actualUserList.contains(user2));
    }

    @Test
    public void getUserById_gotUser() throws UserException {
        // arrange
        Long expectedId = 1L;
        User expectedUser = User.builder()
                .withId(expectedId)
                .build();

        // act
        when(userServiceMock.findById(1L)).thenReturn(expectedUser);

        // assert
        ResponseEntity<?> response = userController.findById(1L);

        verify(userServiceMock, atLeastOnce()).findById(expectedId);

        Assertions.assertEquals(expectedUser, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postNewUser_savesUser() throws UserException {
        // arrange
        Long expectedId = 1L;
        UserDTO userDTO = UserDTO.builder()
                .name("TestUser")
                .email("test@gtest.com")
                .build();
        User expectedUser = User.builder()
                .withId(expectedId)
                .withName("TestUser")
                .withEmail("test@gtest.com")
                .build();

        User expectedResponse = expectedUser;

        when(userServiceMock.saveUser(userDTO)).thenReturn(expectedUser);

        // act
        ResponseEntity<?> actualResponse = userController.register(userDTO);
        System.out.println(actualResponse.getBody());
        // arrange
        Assertions.assertEquals(expectedResponse, actualResponse.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
    }
}
