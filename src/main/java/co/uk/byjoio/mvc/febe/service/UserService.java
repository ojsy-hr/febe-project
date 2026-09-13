package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);
    void save(WebUser webUser);
}
