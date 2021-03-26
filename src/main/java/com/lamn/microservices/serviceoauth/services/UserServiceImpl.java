package com.lamn.microservices.serviceoauth.services;

import brave.Tracer;
import com.lamn.microservices.serviceoauth.clients.UserRestClient;
import com.lamn.microservices.userscommons.models.entity.User;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRestClient userRestClient;
    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRestClient.findByUsername(username);

            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(simpleGrantedAuthority -> LOG.info("The user [{}] has the role [{}] during the login process", username, simpleGrantedAuthority.getAuthority()))
                    .collect(Collectors.toList());

            LOG.info("The user [{}] has been authenticated", username);

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getEnabled(), true, true, true, authorities);
        } catch (FeignException e) {
            LOG.error("The username [{}] does not exist", username);
            tracer.currentSpan().tag("error.message", "The username " + username + " does not exist");
            throw new UsernameNotFoundException("Error authenticating the user");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRestClient.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return userRestClient.update(user, id);
    }
}
