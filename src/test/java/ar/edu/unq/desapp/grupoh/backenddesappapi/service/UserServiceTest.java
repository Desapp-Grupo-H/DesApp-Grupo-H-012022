package ar.edu.unq.desapp.grupoh.backenddesappapi.service;

import ar.edu.unq.desapp.grupoh.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoh.backenddesappapi.model.exceptions.UserException;
import ar.edu.unq.desapp.grupoh.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.TokenDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserLoginDTO;
import ar.edu.unq.desapp.grupoh.backenddesappapi.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void findAllReturnsUserList() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(user1);
        expectedUserList.add(user2);

        when(userRepository.findAll()).thenReturn(expectedUserList);

        List<User> actualUserList = userService.findAll();

        verify(userRepository, atLeastOnce()).findAll();
        verifyNoMoreInteractions(userRepository);

        assertEquals(2, actualUserList.size());
        assertTrue(actualUserList.contains(user1));
        assertTrue(actualUserList.contains(user2));
    }

    @Test
    public void findByIdReturnsUser() throws UserException {
        User expectedUser = mock(User.class);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(expectedUser));

        User actualUser = userService.findById(1L);

        verify(userRepository, atLeastOnce()).findById(1L);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByIdWhenUserDoesNotExistReturnsNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                UserException.class,
                () -> userService.findById(1L)
        );
        String actualMessage = exception.getMessage();
        String expectedMessage = "The User does not exist";

        verify(userRepository, atLeastOnce()).findById(1L);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void findByEmailReturnsUser() throws UserException {
        User expectedUser = mock(User.class);
        Optional optional = Optional.of(expectedUser);
        String email = anyString();

        when(userRepository.findByEmail(email)).thenReturn(optional);

        User actualUser = userService.findByEmail(email);

        verify(userRepository, atLeastOnce()).findByEmail(email);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByEmailWhenUserDoesNotExistReturnsNull() {
        String email = anyString();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                UserException.class,
                () -> userService.findByEmail(email)
        );
        String actualMessage = exception.getMessage();
        String expectedMessage = "The User does not exist";

        verify(userRepository, atLeastOnce()).findByEmail(email);
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void deleteUserById() {
        userService.deleteUser(1L);

        verify(userRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    public void saveUserTransformsDtoIntoModelObject() throws Throwable {
        UserDTO userDTO = mock(UserDTO.class);
        User user = mock(User.class);

        when(userDTO.getName()).thenReturn("name0");
        when(userDTO.getLastName()).thenReturn("lastname0");
        when(userDTO.getEmail()).thenReturn("email0@mail.com");
        when(userDTO.getPassword()).thenReturn("Password#0");
        when(userDTO.getAddress()).thenReturn("anAddress0");
        when(userDTO.getCvu()).thenReturn("1000000000000000000000");
        when(userDTO.getWalletAddress()).thenReturn("10000000");
        when(userRepository.save(any())).thenReturn(user);

        User actualUser = userService.saveUser(userDTO);

        verify(userRepository, atLeastOnce()).save(any());
        verify(userRepository, atLeastOnce()).findByEmail(any());
        verify(userDTO, atLeastOnce()).getName();
        verify(userDTO, atLeastOnce()).getLastName();
        verify(userDTO, atLeastOnce()).getEmail();
        verify(userDTO, atLeastOnce()).getPassword();
        verify(userDTO, atLeastOnce()).getAddress();
        verify(userDTO, atLeastOnce()).getCvu();
        verify(userDTO, atLeastOnce()).getWalletAddress();
        verifyNoMoreInteractions(userDTO, userRepository);

        assertEquals(user, actualUser);
    }

    @Test
    public void loginUserReturnsAToken(){
        UserLoginDTO userLoginDTO = mock(UserLoginDTO.class);
        User user = mock(User.class);
        Optional optional = Optional.of(user);
        when(userLoginDTO.getEmail()).thenReturn("aVali@mail.com");
        when(userLoginDTO.getPassword()).thenReturn("aValiPassword");
        when(user.getPassword()).thenReturn("aValiPassword");

        when(userRepository.findByEmail("aVali@mail.com")).thenReturn(optional);

        TokenDTO token = userService.login(userLoginDTO);

        verify(user, atLeastOnce()).getPassword();
        verify(userLoginDTO, atLeastOnce()).getPassword();
        verify(userRepository, atLeastOnce()).findByEmail("aVali@mail.com");
        assertNotNull(token);
    }

    @Test
    public void loginUserFailNoUserfoundByEmailReturnsNull(){
        UserLoginDTO userLoginDTO = mock(UserLoginDTO.class);
        when(userLoginDTO.getEmail()).thenReturn("aVali@mail.com");

        when(userRepository.findByEmail("aVali@mail.com")).thenReturn(Optional.empty());

        TokenDTO token = userService.login(userLoginDTO);

        verify(userRepository, atLeastOnce()).findByEmail("aVali@mail.com");
        assertNull(token);
    }

    @Test
    public void loginUserFailPasswordMismatchReturnsNull(){
        UserLoginDTO userLoginDTO = mock(UserLoginDTO.class);
        User user = mock(User.class);
        Optional optional = Optional.of(user);

        when(userLoginDTO.getEmail()).thenReturn("aVali@mail.com");
        when(userLoginDTO.getPassword()).thenReturn("aValiPassword");
        when(user.getPassword()).thenReturn("anotherValiPassword");
        when(userRepository.findByEmail("aVali@mail.com")).thenReturn(optional);

        TokenDTO token = userService.login(userLoginDTO);

        verify(user, atLeastOnce()).getPassword();
        verify(userLoginDTO, atLeastOnce()).getPassword();
        verify(userRepository, atLeastOnce()).findByEmail("aVali@mail.com");
        assertNull(token);
    }
}
