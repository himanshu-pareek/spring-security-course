package dev.javarush.spring_security.user_management.security;

public class UserEntity {
    private String username;
    private String password;
    private String authority;


    public UserEntity(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthority() {
        return authority;
    }
}
