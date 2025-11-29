package com.example.demo.config;

import com.example.demo.web.Document;
import java.io.Serializable;
import java.util.Objects;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyPermissionEvaluator implements PermissionEvaluator {
  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject,
                               Object permission) {
    if (targetDomainObject instanceof Document document) {
      if (!document.owner().equals(authentication.getName())) {
        return false;
      }
      // "read", "write"
      String authority = (String) permission;
      return authentication.getAuthorities()
          .stream().map(GrantedAuthority::getAuthority)
          .filter(Objects::nonNull)
          .anyMatch(a -> a.equals(authority));
    }
    return false;
  }

  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId,
                               String targetType, Object permission) {
    return false;
  }
}
