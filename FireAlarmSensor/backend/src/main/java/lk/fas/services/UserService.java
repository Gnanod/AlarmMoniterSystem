package lk.fas.services;


import lk.fas.Entity.User;

public interface UserService {
    User addUser(User user);

    boolean loginUser(String username, String password);
}
