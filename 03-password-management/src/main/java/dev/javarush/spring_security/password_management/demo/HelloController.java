package dev.javarush.spring_security.password_management.demo;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping
    public String getHello() {
        return "Hello\n";
    }
}
