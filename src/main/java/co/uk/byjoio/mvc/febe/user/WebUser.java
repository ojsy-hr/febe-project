package co.uk.byjoio.mvc.febe.security;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class User{

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String userName;

    @NotNull(message="is required")
    @Size(min=1,message="is required")
    private String password;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
