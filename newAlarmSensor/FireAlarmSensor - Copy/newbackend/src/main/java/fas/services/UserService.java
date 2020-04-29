package fas.services;




import fas.Entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    boolean loginUser(String username, String password);

    List<User> getLoggedUserDetails();
}
