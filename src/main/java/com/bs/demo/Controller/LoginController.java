package com.bs.demo.Controller;

import com.bs.demo.Entity.User;
import com.bs.demo.Repository.UserRepository;
import com.bs.demo.Service.JwtService;
import com.bs.demo.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Component
@RequestMapping(value = "/login")
public class LoginController {

  
  private UserRepository userRepo;
  private JwtService jwtService;

  @Autowired
  LoginController(UserRepository userRepo, JwtService jwtService){
    this.userRepo = userRepo;
    this.jwtService = jwtService;
  }

  @PostMapping(value = "")
  public Object login(@RequestBody User user, HttpServletResponse response) {
    System.out.println(user.toString());
    if (!userRepo.existsByUserId(user.getUserId()))
      return "존재하지 않는 아이디입니다.";
    
    BCryptPasswordEncoder bct = new BCryptPasswordEncoder();

    User data = userRepo.findByUserId(user.getUserId());
    if (!bct.matches(user.getUserPassword(), data.getUserPassword())){
      return "비밀번호가 틀립니다.";
    }
  
    String token = jwtService.create(user);
    Cookie cookie = new Cookie("test", token);
    cookie.setMaxAge(60 * 60);
    cookie.setHttpOnly(false);
    cookie.setSecure(false);
    response.addCookie(cookie);
    return Result.SUCCESS.toResponse(HttpStatus.ACCEPTED);
  }

  @PostMapping(value="/info")
  public Object info(@RequestBody User user, HttpServletRequest request){

    String info = "test123";

    Map<String, Object> result = new HashMap<>();

    HttpStatus status = null;

    result.putAll(jwtService.get(request.getHeader("jwt-auth-token")));

    result.put("status", true);
    result.put("info", info);
    result.put("request_body", user);
    status = HttpStatus.ACCEPTED;

    return new ResponseEntity<Map<String, Object>>(result, status);
  }

  @DeleteMapping(value = "")
  public Object logout(@RequestBody User user, HttpServletResponse response) {

    Cookie cookie = new Cookie(user.getUserId(), null);

    cookie.setMaxAge(0);

    response.addCookie(cookie);

    return "로그 아웃에 성공하셨습니다.";

  }

}
