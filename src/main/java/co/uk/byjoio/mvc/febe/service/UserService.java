package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    /**
     * @param email to find user by
     * @return user object
     */
    Optional<User> findByEmail(String email);

    /**
     * @return all users
     */
    List<User> findAll();

    /**
     * @return all roles
     */
    List<Role> findAllRoles();

    /**
     * @param webUser to register in database
     */
    void register(WebUser webUser);

    /**
     * @param user object to update
     * @param roleIds to update
     */
    void updateAccount(User user, Collection<Integer> roleIds);
}
