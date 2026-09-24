package co.uk.byjoio.mvc.febe.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Object of a user populated from the browser on sign-up
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
public class WebUser {

    /**
     * Email of the user
     */
    @NotBlank(message = "is required")
    @Email(message = "must be a valid email address")
    @Size(max = 75, message = "must be 75 characters or fewer")
    private String email;

    /**
     * Password of the user
     */
    @NotBlank(message = "is required")
    private String password;

    /**
     * Hint phrase of the user - used to remind the user of their password
     */
    @NotBlank(message = "is required")
    @Size(max = 16, message = "must be 16 characters or fewer")
    private String hintPhrase;

    public WebUser() {
    }

    /**
     * @param email of the user
     * @param password of the user
     * @param hintPhrase of the user - used to remind the user of their password
     */
    public WebUser(String email, String password, String hintPhrase) {
        this.email = email;
        this.password = password;
        this.hintPhrase = hintPhrase;
    }

    /**
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return hint phrase of the user
     */
    public String getHintPhrase() {
        return hintPhrase;
    }

    /**
     * @param hintPhrase of the user
     */
    public void setHintPhrase(String hintPhrase) {
        this.hintPhrase = hintPhrase;
    }
}
