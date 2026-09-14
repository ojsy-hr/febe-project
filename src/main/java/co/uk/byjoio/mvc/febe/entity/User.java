package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private int userId;

    @Column(name="email", length=75, nullable=false, unique=true)
    private String email;

    @Column(name="password", length=68, nullable=false)
    private String password;

    @Column(name="hint_phrase", length=16, nullable=false)
    private String hintPhrase;

    @Column(name="created_date", nullable=false, updatable=false)
    private LocalDateTime createdDate;

    @Column(name="modified_date", nullable=false)
    private LocalDateTime modifiedDate;

    @Column(name="enabled", nullable=false)
    private boolean enabled;

    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="users_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles=new LinkedHashSet<>();

    public User() {
    }

    public User(String email, String password, String hintPhrase) {
        this.email=email;
        this.password=password;
        this.hintPhrase=hintPhrase;
    }

    @PrePersist
    void onCreate() {
        createdDate = LocalDateTime.now();
        modifiedDate = createdDate;
    }

    @PreUpdate
    void onUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{userId = " + userId
                + ", email = '" + email + "'"
                + ", createdDate = " + createdDate
                + ", modifiedDate = " + modifiedDate
                + ", enabled = " + enabled
                + ", roles = " + roles
                + "}";
    }
}
