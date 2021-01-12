package com.bs.demo.Controller;

import com.bs.demo.Entity.User;
import com.bs.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Component
@RequestMapping(value="/login")
public class LoginController {

    @Autowired(required = false)
    private UserRepository userRepo;

    @PostMapping(value="")
    public Object login(@RequestBody User user, HttpServletResponse response){
        if(!userRepo.existsByUserId(user.getUserId())) return "존재하지 않는 아이디입니다.";

        BCryptPasswordEncoder bct =  new BCryptPasswordEncoder();

        User data = userRepo.findByUserId(user.getUserId());
        if(!bct.matches(user.getUserPassword(), data.getUserPassword())) return "비밀번호가 틀립니다.";

        Cookie setCookie = new Cookie(user.getUserId(), "c");

        setCookie.setMaxAge(60*60);

        response.addCookie(setCookie);

        return "로그인에 성공하였습니다.";
    }

    @DeleteMapping(value="")
    public Object logout(@RequestBody User user, HttpServletResponse response){

        Cookie cookie = new Cookie(user.getUserId(), null);

        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return "로그 아웃에 성공하셨습니다.";

    }

}
