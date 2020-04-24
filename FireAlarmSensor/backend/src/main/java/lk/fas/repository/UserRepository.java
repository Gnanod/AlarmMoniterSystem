package lk.fas.repository;

import lk.fas.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "from User where username =?1 and password=?2")
    User loginUser(String username, String password);
}