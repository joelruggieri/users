package com.cashonline.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void whenAddNewUser_thenUserIsCreatedProperly() {
        // given
        User persistedUser = new User();
        persistedUser.setId(1);
        persistedUser.setFirstName("pedro");
        persistedUser.setLastName("rodrigiezx");
        persistedUser.setEmail("pepe@gmail.com");

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(persistedUser);

        // when
        User newUser = userService.newUser("pedro", "rodrigiezx", "pepe@gmail.com");

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        Assertions.assertEquals(persistedUser.getId(), newUser.getId());
        Assertions.assertEquals(persistedUser.getFirstName(), newUser.getFirstName());
        Assertions.assertEquals(persistedUser.getLastName(), newUser.getLastName());
        Assertions.assertEquals(persistedUser.getEmail(), newUser.getEmail());
    }

    @Test
    public void whenFindUser_thenPersistedUserIsReturned() {
        // given
        User persistedUser = new User();
        persistedUser.setId(1);
        persistedUser.setFirstName("pedro");
        persistedUser.setLastName("rodrigiezx");
        persistedUser.setEmail("pepe@gmail.com");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(persistedUser));

        // when
        Optional<User> userOptional = userService.findUserById(1L);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
        Assertions.assertTrue(userOptional.isPresent());
        Assertions.assertEquals(persistedUser.getId(), userOptional.get().getId());
        Assertions.assertEquals(persistedUser.getFirstName(), userOptional.get().getFirstName());
        Assertions.assertEquals(persistedUser.getLastName(), userOptional.get().getLastName());
        Assertions.assertEquals(persistedUser.getEmail(), userOptional.get().getEmail());
    }

    @Test
    public void whenDeleteUser_thenRepositoryIsCalledToDeleteUser() throws UserNotFoundException {
        // given
        User persistedUser = new User();
        persistedUser.setId(1);
        persistedUser.setFirstName("pedro");
        persistedUser.setLastName("rodrigiezx");
        persistedUser.setEmail("pepe@gmail.com");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(persistedUser));

        // when
        userService.deleteUserById(1L);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
        Mockito.verify(userRepository, Mockito.times(1)).delete(persistedUser);
    }

    @Test
    public void whenTryToDeleteNonexistentUser_thenExceptionIsThrown() throws UserNotFoundException {
        // given
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));

        // then
        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
        Mockito.verify(userRepository, Mockito.times(0)).delete(Mockito.any());
    }
}
