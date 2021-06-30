package com.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nbs.model.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>  {
public User findByEmailAndPassword(String email,String password);
}
