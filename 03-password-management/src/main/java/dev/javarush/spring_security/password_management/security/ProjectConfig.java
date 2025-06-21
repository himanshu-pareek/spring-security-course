package dev.javarush.spring_security.password_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {
    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder encoder) {
        UserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        var admin = User.withUsername("admin")
                .password(encoder.encode("password"))
                .roles("USER", "ADMIN")
                .build();

        var user = User.withUsername("user")
                .password("{noop}1234")
                .roles("USER")
                .build();

        if (!manager.userExists(admin.getUsername())) {
            manager.createUser(admin);
        }

        if (!manager.userExists(user.getUsername())) {
            manager.createUser(user);
        }

        return manager;
    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        String idToEncode = "bcrypt";
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put(idToEncode, new BCryptPasswordEncoder());
//        encoders.put("noop", NoOpPasswordEncoder.getInstance());
//        encoders.put("sha256", new StandardPasswordEncoder());
//
//        // {noop}password -> password, typed password
//        // {sha256}lmsofhjs -> lmsofhjs, typed password
//
//        return new DelegatingPasswordEncoder(idToEncode, encoders);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
