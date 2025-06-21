package dev.javarush.spring_security.user_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        var user1 = User.withUsername("javarush")
                .password("{noop}password")
                .roles("AMIN", "USER")
                .build();
        var user2 = User.withUsername("bob")
                .password("{noop}1234")
                .roles("USER")
                .build();
        UserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if (!manager.userExists(user1.getUsername()))
            manager.createUser(user1);
        if (!manager.userExists(user2.getUsername()))
            manager.createUser(user2);
        return manager;
    }
}
