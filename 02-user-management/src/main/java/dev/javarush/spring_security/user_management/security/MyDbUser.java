package dev.javarush.spring_security.user_management.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyDbUser implements UserDetails {
    private final UserEntity user;


    public MyDbUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = this.user.getAuthority();
        return List.of(() -> authority);
    }
}
