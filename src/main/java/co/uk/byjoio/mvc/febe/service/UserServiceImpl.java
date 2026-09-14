package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.repository.RoleRepository;
import co.uk.byjoio.mvc.febe.repository.UserRepository;
import co.uk.byjoio.mvc.febe.user.WebUser;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "ROLE_MEMBER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll(Sort.by("role"));
    }

    @Override
    @Transactional
    public void register(WebUser webUser) {

        // define default role for new user
        Role defaultRole = roleRepository.findByRole(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException(DEFAULT_ROLE + " is missing from the roles table"));

        // translate webUser form data to user entity
        User user = new User(webUser.getEmail(), passwordEncoder.encode(webUser.getPassword()), webUser.getHintPhrase());
        user.setEnabled(true);
        // assign default role
        user.setRoles(Set.of(defaultRole));

        // save user
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateAccount(User user, Collection<Integer> roleIds) {

        // Check user exists
        User existing = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("No user with id " + user.getUserId()));

        // update email
        existing.setEmail(user.getEmail());
        // update enabled
        existing.setEnabled(user.isEnabled());
        // grant/revoke roles
        existing.setRoles(new LinkedHashSet<>(roleRepository.findAllById(roleIds)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // find user
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));


        return withUsername(user.getEmail())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .authorities(mapRolesToAuthorities(user.getRoles()))
                .build();
    }

    private List<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();
    }
}
