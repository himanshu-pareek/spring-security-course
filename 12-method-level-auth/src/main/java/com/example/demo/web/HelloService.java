package com.example.demo.web;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
  // @PreAuthorize("#name == authentication.name && hasAuthority('write')") // hasRole('admin') <=> hasAuthority('ROLE_admin')
  // hasAnyAuthority('write', 'edit'), hasAnyRole('admin', 'manager')
  // @PostAuthorize("returnObject.owner == authentication.name")
  @PostAuthorize("hasPermission(returnObject, 'read')")
    public Document hello(String documentId) {
      return new Document("java");
    }
}

