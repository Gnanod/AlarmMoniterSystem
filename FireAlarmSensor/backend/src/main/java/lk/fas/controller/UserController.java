package lk.fas.controller;

import lk.fas.Entity.Sensor;
import lk.fas.Entity.User;
import lk.fas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/userController")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(value = "addUser")
    public User addUser(@RequestBody User user) {

        return userService.addUser(user);

    }

    @GetMapping(value = "loginUser/{username}/{password}")
    public boolean loginUser(@PathVariable String username,@PathVariable String password) {

        return userService.loginUser(username,password);

    }
}
