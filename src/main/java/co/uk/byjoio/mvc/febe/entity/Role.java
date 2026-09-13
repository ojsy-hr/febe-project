package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.*;

@Entity(name="roles")
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;

    @Column(name="role")
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }
}
