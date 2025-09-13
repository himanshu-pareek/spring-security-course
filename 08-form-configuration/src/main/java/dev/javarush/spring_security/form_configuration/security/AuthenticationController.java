package dev.javarush.spring_security.form_configuration.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class AuthenticationController {
  @GetMapping
  public String loginPage() {
    return "login.html";
  }
}
