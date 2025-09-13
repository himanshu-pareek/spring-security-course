package dev.javarush.spring_security.form_configuration.web;

import java.util.Map;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
  @GetMapping
  public Map<String, String> hello() {
    return Map.of(
        "message", "Hello " + loggedInUsername()
    );
  }

  private static String loggedInUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
