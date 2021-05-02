package com.cashonline.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User newUser(String firstName, String lastName, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id.intValue());
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id.intValue()).orElseThrow(() -> new UserNotFoundException(id.intValue()));
        userRepository.delete(user);
    }
}
