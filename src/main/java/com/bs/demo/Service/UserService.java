package com.bs.demo.Service;

import com.bs.demo.Entity.User;
import com.bs.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepo;

  @Autowired
  public UserService(UserRepository userRepo){
    this.userRepo = userRepo;
  }

  public Optional<User> findById(Integer id) {
    return userRepo.findById(id);
  }

}
