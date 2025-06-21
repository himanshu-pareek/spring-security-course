package dev.javarush.spring_security.user_management.security;

import org.springframework.security.core.userdetails.User;

public class UserDemo {
    void demo() {
        var user = User.withUsername("javarush")
                .password("password")
                .authorities("ROLE_user", "ROLE_admin")
                .build();

        var user2 = User.withUserDetails(user)
                .username("bob")
                .password("pass")
                .build();
    }
}
