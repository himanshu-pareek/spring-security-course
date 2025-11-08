package dev.javarush.spring_security_course.csrf.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
class HelloController {
  @GetMapping
  String getHello() {
    return "GET /hello\n";
  }

  @PostMapping
  String postHello() {
    return "POST /hello\n";
  }
}
