package co.uk.byjoio.mvc.febe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Table entity for roles stored in MySQL DB
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Entity
@Table(name="roles")
public class Role {

    /**
     * The ID (pk) of the role
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="role_id")
    private int roleId;

    /**
     * The name of the role
     */
    @Column(name="role", length=64, nullable=false, unique=true)
    private String role;

    public Role() {
    }

    /**
     * @param role name
     */
    public Role(String role) {
        this.role = role;
    }

    /**
     * @return the ID(pk) of the role
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId of the role
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * @return name of the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role name
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{roleId=" + roleId + ", role='" + role + "'}";
    }
}
