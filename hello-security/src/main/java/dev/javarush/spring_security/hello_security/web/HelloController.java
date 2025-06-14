package dev.javarush.spring_security.hello_security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping
    public String getHello() {
        return "Hello\n";
    }
}
