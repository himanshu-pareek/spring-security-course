package dev.javarush.spring_security_course.csrf.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:8081" })
class HelloController {
  @GetMapping("/hello")
  String getHello() {
    return "GET /hello\n";
  }

  @PostMapping("/transfer")
  String postHello(@RequestParam("amount") String amount, @RequestParam("to") String to) {
    return String.format("Sent %s to %s\n", amount, to);
  }
}
