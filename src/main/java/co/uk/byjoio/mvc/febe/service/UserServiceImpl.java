package co.uk.byjoio.mvc.febe.service;

import co.uk.byjoio.mvc.febe.dao.RoleDao;
import co.uk.byjoio.mvc.febe.dao.UserDao;
import co.uk.byjoio.mvc.febe.entity.Role;
import co.uk.byjoio.mvc.febe.entity.User;
import co.uk.byjoio.mvc.febe.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByUserName(String userName) {

        return userDao.findByUserName(userName);
    }

    @Override
    public void save(WebUser webUser) {

        User user = new User();

        user.setEmail(webUser.getEmail());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setHintPhrase(webUser.getHintPhrase());
        user.setEnabled(true);

        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(authority);
        }

        return authorities;
    }
}