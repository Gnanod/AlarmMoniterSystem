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


    // Add User Details To Database
    @PostMapping(value = "addUser")
    public User addUser(@RequestBody User user) {

        return userService.addUser(user);

    }

    // Check user credentials using username and password
    @GetMapping(value = "loginUser/{username}/{password}")
    public boolean loginUser(@PathVariable String username,@PathVariable String password) {
        return userService.loginUser(username,password);

    }
}
