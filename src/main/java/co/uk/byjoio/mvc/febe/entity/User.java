package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Table entity for users stored in MySQL DB
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Entity
@Table(name="users")
public class User {

    /**
     * The ID (pk) of the user
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private int userId;

    /**
     * The email of the user
     */
    @Column(name="email", length=75, nullable=false, unique=true)
    private String email;

    /**
     * The password of the user (stored as a bcrypt value)
     */
    @Column(name="password", length=68, nullable=false)
    private String password;

    /**
     * The hint phrase of the user - used to remind the user of their password
     */
    @Column(name="hint_phrase", length=16, nullable=false)
    private String hintPhrase;

    /**
     * The date the user account was created
     */
    @Column(name="created_date", nullable=false, updatable=false)
    private LocalDateTime createdDate;

    /**
     * The date the user account was last modified
     */
    @Column(name="modified_date", nullable=false)
    private LocalDateTime modifiedDate;

    /**
     * True if user account enabled, false otherwise
     */
    @Column(name="enabled", nullable=false)
    private boolean enabled;

    /**
     * Roles a user has. Link stored in users_roles table
     * User can have many Roles, roles can be assigned to many users
     */
    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="users_roles",
            // join on users_roles.user_id = users.
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    /**
     * A user can only have one profile and vice versa.
     * On modify, cascade to user profile and vice versa
     */
    @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="users_profiles",
            // join on users_profiles.user_profile_id = users.user_id
            joinColumns=@JoinColumn(name="user_profile_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private UserProfile userProfile;

    public User(){
    }

    /**
     * @param email of the user
     * @param password of the user
     * @param hintPhrase of the user - used to remind the user of their password
     */
    public User(String email, String password, String hintPhrase) {
        this.email=email;
        this.password=password;
        this.hintPhrase=hintPhrase;
    }

    /**
     * Set created date and modified date on user creation
     */
    @PrePersist
    void onCreate() {
        createdDate = LocalDateTime.now();
        modifiedDate = createdDate;
    }

    /**
     * Set modified date on user modification
     */
    @PreUpdate
    void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    /**
     * @return ID(pk) of the suer
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @param password of the suer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return hit phrase of the user
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

    /**
     * @return creation date of the user
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @return modified date of the user
     */
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @return true if user enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled true if user enabled, false otherwise
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return roles of user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles to set on user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * @param createdDate of the user
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @param modifiedDate the last time the user account was updated
     */
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * @return the user's profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * @param userProfile to update
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hintPhrase='" + hintPhrase + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", userProfile=" + userProfile +
                '}';
    }
}