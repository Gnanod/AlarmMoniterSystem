package fas.controller;


import fas.Entity.User;
import fas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

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


        //Decoded UserName
        byte[] decodedBytesUserName = Base64.getDecoder().decode(username);
        String decodedUserName = new String(decodedBytesUserName);

        //Decoded Password
        byte[] decodedBytesPassword = Base64.getDecoder().decode(password);
        String decodedPassword = new String(decodedBytesPassword);

        return userService.loginUser(decodedUserName,decodedPassword);

    }
}
