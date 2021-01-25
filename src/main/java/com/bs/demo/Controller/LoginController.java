package com.bs.demo.Controller;

import com.bs.demo.Entity.User;
import com.bs.demo.Repository.UserRepository;
import com.bs.demo.Service.JwtService;
import com.bs.demo.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    Cookie cookie = new Cookie(user.getUserId(), token);
    cookie.setMaxAge(60 * 60);
    cookie.setHttpOnly(false);
    cookie.setSecure(false);
    response.addCookie(cookie);
    return Result.SUCCESS.toResponse(HttpStatus.ACCEPTED);
  }

  @PostMapping(value="/info")
  public Object info(@RequestBody User user, HttpServletRequest request){

    Cookie[] token = request.getCookies();

    for(int i = 0;i < token.length;i++){
      System.out.println(token[i].getName() + " token: " + token[i].getValue());
      jwtService.checkValid(token[i].getValue());
    }


    return Result.SUCCESS.toResponse(HttpStatus.OK);
  }

  @DeleteMapping(value = "")
  public Object logout(@RequestBody User user, HttpServletResponse response) {

    System.out.println(user.getUserId());

    Cookie cookie = new Cookie(user.getUserId(), null);
  
    cookie.setMaxAge(0);

    response.addCookie(cookie);

    return Result.SUCCESS.toResponse(HttpStatus.OK);

  }

}
