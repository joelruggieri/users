package com.cashonline.http.user;

import com.cashonline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.cashonline.model.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/{id}")
    public @ResponseBody
    String getUser (@PathVariable String id) {
        return "OK";
    }

    @PostMapping()
    public ResponseEntity createUser(@Valid @RequestBody CreateUserInput input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
