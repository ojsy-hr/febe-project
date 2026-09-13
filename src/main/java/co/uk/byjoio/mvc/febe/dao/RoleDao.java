package co.uk.byjoio.mvc.febe.dao;

import co.uk.byjoio.mvc.febe.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String roleName);
}
