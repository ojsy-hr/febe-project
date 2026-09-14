package co.uk.byjoio.mvc.febe.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class WebUser {

    @NotBlank(message = "is required")
    @Email(message = "must be a valid email address")
    @Size(max = 75, message = "must be 75 characters or fewer")
    private String email;

    @NotBlank(message = "is required")
    private String password;

    @NotBlank(message = "is required")
    @Size(max = 16, message = "must be 16 characters or fewer")
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
