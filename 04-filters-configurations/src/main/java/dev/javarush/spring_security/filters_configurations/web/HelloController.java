package dev.javarush.spring_security.filters_configurations.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping
    public String get() {
        return "Hello\n";
    }
}
