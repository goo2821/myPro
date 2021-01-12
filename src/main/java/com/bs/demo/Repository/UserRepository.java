package com.bs.demo.Repository;

import com.bs.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByUserId(String userId);
    User findByUserId(String userId);
}