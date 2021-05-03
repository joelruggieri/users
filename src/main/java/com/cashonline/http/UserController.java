package com.cashonline.http;

import com.cashonline.http.input.CreateUserInput;
import com.cashonline.http.output.LoanResponse;
import com.cashonline.http.output.UserResponse;
import com.cashonline.model.user.User;
import com.cashonline.model.user.UserNotFoundException;
import com.cashonline.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity createUser(@Valid @RequestBody CreateUserInput input) {
        userService.newUser(input.getFirstName(), input.getLastName(), input.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    ResponseEntity getUser (@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<LoanResponse> loans = user.get().getLoans().stream().map(loan ->
                new LoanResponse(loan.getId(), loan.getUserId(), loan.getAmount().doubleValue()))
                .collect(Collectors.toList());
        UserResponse response = new UserResponse(
                user.get().getId(),
                user.get().getFirstName(),
                user.get().getLastName(),
                user.get().getEmail(),
                loans
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
