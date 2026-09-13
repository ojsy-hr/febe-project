package co.uk.byjoio.mvc.febe.dao;

import co.uk.byjoio.mvc.febe.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    void save(User user);
}
