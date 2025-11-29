package com.example.demo.web;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableMethodSecurity
public class HelloController {

    private HelloService service;

    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping("/hello/{name}")
    public Document hello(@PathVariable("name") String name) {
        return this.service.hello(name);
    }

}
