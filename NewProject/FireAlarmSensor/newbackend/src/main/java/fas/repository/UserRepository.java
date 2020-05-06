package fas.repository;


import fas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    //Check User Login Credentials
    @Query(value = "from User where username =?1 and password=?2")
    User loginUser(String username, String password);

    //Get Logged User Details
    @Query(value = "from User where userStatus ='Logged'")
    List<User> getLoggedUserDetails();
}
