package http.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(path="/users")
public class UserController {

    @GetMapping(path="/{id}")
    public @ResponseBody
    String getUser (@PathVariable String id) {
        return "OK";
    }
}
