package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.entity.UserProfile;
import co.uk.byjoio.mvc.febe.repository.RoleRepository;
import co.uk.byjoio.mvc.febe.repository.UserProfileRepository;
import co.uk.byjoio.mvc.febe.repository.UserRepository;
import co.uk.byjoio.mvc.febe.user.WebUser;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.security.core.userdetails.User.withUsername;

/**
 * Service to fetch/modify user data
 *
 * @author ojsy-hr
 * @version v1.0.0
 * @since 13-09-2026
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * The default role given to all new users at sign-up
     */
    private static final String DEFAULT_ROLE = "ROLE_MEMBER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * JPA repository for user data
     */
    private final UserRepository userRepository;

    /**
     * JPA repository for role data
     */
    private final RoleRepository roleRepository;

    /**
     * JPA repository for user profile data
     */
    private final UserProfileRepository userProfileRepository;

    /**
     * Password encoder object to apply to passwords before storing in DB
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userRepository for user data
     * @param roleRepository for role data
     * @param passwordEncoder to apply to users passwords
     */
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param email to find user by
     * @return user object
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * @return all users in a list
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * @return all roles in a list
     */
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll(Sort.by("role"));
    }

    /**
     * @param webUser to register in database
     */
    @Override
    @Transactional
    public void register(WebUser webUser) {

        System.out.println("register - start");

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

        System.out.println("register:userProfile - create");
        UserProfile userProfile = new UserProfile(user.getUserId(),"","","",0,new Date());
        System.out.println(userProfile.toString());
        System.out.println("register:userProfile - save");
        userProfileRepository.save(userProfile);
        System.out.println("register - end");
    }

    /**
     * @param user object to update
     * @param roleIds to update
     */
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

    /**
     * @param username identifying the user whose data is required.
     * @return UserDetails to log in with
     * @throws UsernameNotFoundException if invalid creds supplied
     */
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

    /**
     * @param roles to map to spring authorities
     * @return list of authorities spring can interpret
     */
    private List<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();
    }

    /**
     * @return user profile to display on profile page
     */
    @Override
    public Optional<UserProfile> findUserProfileById() {
        return userProfileRepository.findById(getAuthenticatedUserId());
    }

    /**
     * @return user profile to display on profile page
     */
    @Override
    public Optional<UserProfile> findUserProfileById(int userId) {
        return userProfileRepository.findById(userId);
    }

    /**
     * @return current authenticated user id
     */
    private int getAuthenticatedUserId(){
        //TODO: Add good validation in here for work beyond POC

        // get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)auth.getPrincipal();
        // find user in db
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        // return id used to find user profile
        return user.getUserId();
    }

    private void insertDefaultRole(String role){
        Role defaultRole = new Role(role);
        roleRepository.save(defaultRole);
    }

    @PostConstruct
    @Transactional
    protected void checkForDefaultRole() {

        if (roleRepository.findByRole(DEFAULT_ROLE).isEmpty()) {
            insertDefaultRole(DEFAULT_ROLE);
        }

        if (roleRepository.findByRole(ADMIN_ROLE).isEmpty()) {
            insertDefaultRole(ADMIN_ROLE);
        }
    }

    @Transactional
    public void saveUserProfile(UserProfile profile){

        userProfileRepository.save(profile);
    }
}