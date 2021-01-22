package com.bs.demo.Controller;

import com.bs.demo.Entity.User;
import com.bs.demo.utils.Result;

import com.bs.demo.Repository.UserRepository;
import com.bs.demo.utils.ModelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Component
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

  private final UserRepository userRepo;

  @Autowired
  UserController(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @GetMapping(value = "")
  public Iterable<User> findAllUser() {
    log.info("find all!");
    return userRepo.findAll();

  }

  @GetMapping(value = "/{idx}")
  public Object findUser(@PathVariable int idx) {
    if (!userRepo.existsById(idx))
      return Result.USER_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);

    Optional<User> user = userRepo.findById(idx);
    if (user.isEmpty())
      return Result.USER_NOT_FOUND.toResponse(HttpStatus.BAD_REQUEST);
    return user.get();

  }

  @PostMapping(value = "")
  public Object addUser(@RequestBody User user) {
    log.info("log: " + user);
    if (userRepo.existsByUserId(user.getUserId())) {
      System.out.println("존재하는 아이디입니다");
      return "이미 존재합니다";
    }
    BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();

    String pw = user.getUserPassword();

    String hash = bcp.encode(pw);

    user.setUserPassword(hash);

    user.setUserRole("normal");

    userRepo.save(user);
    return "성공적으로 등록 되었습니다";
  }

  @PutMapping(value = "{idx}")
  public Object updateUser(@PathVariable int idx, @RequestBody User user) {
    // Model 사용해서 교체해보기!!!!
    if (!userRepo.existsById(idx)) {
      return "존재하지 않는 아이디입니다.";
    }

    Optional<User> origin = userRepo.findById(idx);
    if (origin.isEmpty())
      return "회원이 존재하지 않습니다.";
    ModelUtils.fillNewModel(user, origin.get());

    userRepo.save(user);

    return "수정이 완료되었습니다";

  }

  @DeleteMapping(value = "{idx}")
  public Object removeUser(@PathVariable int idx) {

    if (userRepo.existsById(idx)) {
      return "존재하지 않는 아이디입니다.";
    }

    userRepo.deleteById(idx);
    return "성공적으로 삭제되었습니다.";
  }

}
