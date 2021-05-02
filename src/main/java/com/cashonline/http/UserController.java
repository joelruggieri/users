package com.cashonline.http;

import com.cashonline.http.input.CreateUserInput;
import com.cashonline.http.output.LoanResponse;
import com.cashonline.http.output.UserResponse;
import com.cashonline.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.cashonline.model.user.UserRepository;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public ResponseEntity createUser(@Valid @RequestBody CreateUserInput input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    ResponseEntity getUser (@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id.intValue());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        UserResponse response = new UserResponse();
        response.setId(user.get().getId());
        response.setFirstName(user.get().getFirstName());
        response.setLastName(user.get().getLastName());
        response.setEmail(user.get().getEmail());
        response.setLoans(user.get().getLoans().stream().map(loan -> {
            LoanResponse loanResponse  = new LoanResponse();
            loanResponse.setId(loan.getId());
            loanResponse.setUserId(loan.getUserId());
            loanResponse.setTotal(loan.getAmount().doubleValue());
            return loanResponse;
        }).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id.intValue());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userRepository.delete(user.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
