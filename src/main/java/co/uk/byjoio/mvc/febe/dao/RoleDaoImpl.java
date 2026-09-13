package co.uk.byjoio.mvc.febe.dao;

import co.uk.byjoio.mvc.febe.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao{

    @Autowired
    private EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String roleName){

        TypedQuery<Role> query = entityManager.createQuery("from roles where role=:roleName", Role.class);
        query.setParameter("roleName", roleName);

        Role role = null;

        try{
            role = query.getSingleResult();
        }catch(Exception e){
            role = null;
        }

        return role;
    }
}