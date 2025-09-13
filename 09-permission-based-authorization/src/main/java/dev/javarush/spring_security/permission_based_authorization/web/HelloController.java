package dev.javarush.spring_security.permission_based_authorization.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello/{name}")
public class HelloController {
  @GetMapping
  public String hello(@PathVariable("name") String name) {
    return "Hello " + name + "\n";
  }
}
