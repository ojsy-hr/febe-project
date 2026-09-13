package co.uk.byjoio.mvc.febe.dao;

import co.uk.byjoio.mvc.febe.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public User findByUserName(String userName) {

        TypedQuery<User> query = entityManager.createQuery("from users where email=:email and enabled=true", User.class);
        query.setParameter("email", userName);

        User user = null;
        try{
            user = query.getSingleResult();
        }catch(Exception e){
            user = null;
        }

        return user;
    }

    @Override
    @Transactional
    public void save(User user) {

        entityManager.merge(user);
    }
}
