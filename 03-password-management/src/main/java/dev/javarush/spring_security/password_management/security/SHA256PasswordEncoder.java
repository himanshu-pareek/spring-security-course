package dev.javarush.spring_security.password_management.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SHA256PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return sha256Encode((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encoded = sha256Encode((String) rawPassword);
        return encoded.equals(encodedPassword);
    }

    private String sha256Encode(String text) {
        // ...
        return text;
    }
}
