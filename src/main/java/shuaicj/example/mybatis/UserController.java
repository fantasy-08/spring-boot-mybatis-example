package shuaicj.example.mybatis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description.
 *
 * @author shuaicj 2019/07/02
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        userService.insertDuplicate();
        return "hello";
    }
}
