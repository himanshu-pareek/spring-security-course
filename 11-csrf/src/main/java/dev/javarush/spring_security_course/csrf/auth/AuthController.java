package dev.javarush.spring_security_course.csrf.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class AuthController {
  @GetMapping
  String loginPage() {
    return "login";
  }
}
