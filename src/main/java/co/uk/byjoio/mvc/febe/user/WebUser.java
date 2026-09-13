package co.uk.byjoio.mvc.febe.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class WebUser{

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    //@Pattern(regexp="^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull(message="is required")
    @Size(min=1,message="is required")
    private String password;

    @NotNull(message="is required")
    @Size(min=1,message="is required")
    private String hintPhrase;

    public WebUser() {
    }

    public WebUser(String email, String password, String hintPhrase) {
        this.email = email;
        this.password = password;
        this.hintPhrase = hintPhrase;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHintPhrase() {
        return hintPhrase;
    }

    public void setHintPhrase(String hintPhrase) {
        this.hintPhrase = hintPhrase;
    }
}
