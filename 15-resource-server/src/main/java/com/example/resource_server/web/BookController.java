package com.example.resource_server.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {
  @GetMapping
  String list() {
    return "[ List of Books ] - " + getAllRoles();
  }

  @GetMapping("{id}")
  String one() {
    return "[ One Book ] - " + getAllRoles();
  }

  @PostMapping
  String create() {
    return "[ Create Book ] - " + getAllRoles();
  }

  String getAllRoles() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assert authentication != null;
    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .reduce("", (a, b) -> a + ", " + b);
  }
}
