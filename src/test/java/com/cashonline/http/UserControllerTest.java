package com.cashonline.http;

import com.cashonline.http.input.CreateUserInput;
import com.cashonline.http.output.UserResponse;
import com.cashonline.model.user.User;
import com.cashonline.model.user.UserNotFoundException;
import com.cashonline.model.user.UserRepository;
import com.cashonline.model.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.Optional;

@SpringBootTest
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    UserController userController;

    @Test
    public void whenAddNewUser_thenUserIsCreatedProperly() {
        CreateUserInput input = new CreateUserInput();
        input.setFirstName("firstName");
        input.setLastName("lastName");
        input.setEmail("email");

        ResponseEntity response = userController.createUser(input);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Mockito.verify(userService, Mockito.times(1))
                .newUser("firstName", "lastName", "email");
    }

    @Test
    public void whenGetUser_thenUserIsReturned() {
        // given
        User user = new User();
        user.setId(1);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setLoans(Collections.emptyList());

        Mockito.when(userService.findUserById(1L)).thenReturn(Optional.of(user));

        // when
        ResponseEntity response = userController.getUser(1L);
        UserResponse userResponse = ((UserResponse) response.getBody());

        // then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, userResponse.getId());
        Assertions.assertEquals("firstName", userResponse.getFirstName());
        Assertions.assertEquals("lastName", userResponse.getLastName());
        Assertions.assertEquals("email", userResponse.getEmail());
        Assertions.assertTrue(userResponse.getLoans().isEmpty());
        Mockito.verify(userService, Mockito.times(1))
                .findUserById(1L);
    }

    @Test
    public void whenDeleteUser_thenUseDeleted() throws UserNotFoundException {
        ResponseEntity response = userController.deleteUser(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(userService, Mockito.times(1))
                .deleteUserById(1L);
    }

    @Test
    public void whenTryToDeleteNonexistentUser_thenNotFoundIsReturned() throws UserNotFoundException {
        Mockito.doThrow(new UserNotFoundException(1)).when(userService).deleteUserById(1L);

        ResponseEntity response = userController.deleteUser(1L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Mockito.verify(userService, Mockito.times(1))
                .deleteUserById(1L);
    }
}
