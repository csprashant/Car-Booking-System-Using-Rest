package com.nbs.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nbs.model.User;

public interface UserRepository extends JpaRepository<User,Integer>  {
public User findByEmailAndPassword(String email,String password);
@Query("Select u from User u where u.email= :email")
public Optional<User> getUserByEmail(@Param("email") String email);
public User findByName(String name);
}
