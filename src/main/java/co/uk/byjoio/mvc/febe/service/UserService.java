package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<Role> findAllRoles();
    void register(WebUser webUser);
    void updateAccount(User user, Collection<Integer> roleIds);
}
