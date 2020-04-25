package lk.fas.services.impl;


import lk.fas.Entity.User;
import lk.fas.repository.UserRepository;
import lk.fas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean loginUser(String username, String password) {
        User user = userRepository.loginUser(username,password);

        if(user !=null){
            user.setUserStatus("Logged");
            userRepository.save(user);
            return true;


        }else{
            return false;
        }
    }

    @Override
    public List<User> getLoggedUserDetails() {
        return userRepository.getLoggedUserDetails();
    }
}
