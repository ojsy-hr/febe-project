package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity(name="users")
@Table(name="users", schema="febe_project")
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false, unique=true)
    private int userId;

    @Column(name="email", length=75, nullable=false, unique=true)
    private String email;

    @Column(name="password", length=68, nullable=false)
    private String password;

    @Column(name="hint_phrase", length=16, nullable=false)
    private String hintPhrase;

    @Column(name="created_date", length=75, nullable=false)
    private Date createdDate;

    @Column(name="modified_date", length=75, nullable=false)
    private Date modifiedDate;

    @Column(name="enabled", nullable=false)
    private boolean enabled;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="users_roles", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
    private Collection<Role> roles;

    public Users() {
    }

    public Users(String email, String password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public Users(String email, String password, boolean enabled, Collection<Role> roles) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hintPhrase='" + hintPhrase + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
