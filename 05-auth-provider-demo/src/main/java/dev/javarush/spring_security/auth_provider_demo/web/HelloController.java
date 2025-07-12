package dev.javarush.spring_security.auth_provider_demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping
  public String hello() {
    return "Hello, world\n";
  }
}
