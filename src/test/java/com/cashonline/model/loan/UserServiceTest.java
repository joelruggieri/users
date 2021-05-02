package com.cashonline.model.loan;


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
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(newUser.getId()).isEqualTo(persistedUser.getId());
        assertThat(newUser.getFirstName()).isEqualTo(persistedUser.getFirstName());
        assertThat(newUser.getLastName()).isEqualTo(persistedUser.getLastName());
        assertThat(newUser.getEmail()).isEqualTo(persistedUser.getEmail());
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
        assertThat(userOptional.isPresent());
        assertThat(userOptional.get().getId()).isEqualTo(persistedUser.getId());
        assertThat(userOptional.get().getFirstName()).isEqualTo(persistedUser.getFirstName());
        assertThat(userOptional.get().getLastName()).isEqualTo(persistedUser.getLastName());
        assertThat(userOptional.get().getEmail()).isEqualTo(persistedUser.getEmail());
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
