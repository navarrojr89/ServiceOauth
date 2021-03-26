package com.lamn.microservices.serviceoauth.security.event;

import brave.Tracer;
import com.lamn.microservices.serviceoauth.services.UserService;
import com.lamn.microservices.userscommons.models.entity.User;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type Authentication handler.
 */
@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationHandler.class);

    @Autowired
    private Tracer tracer;
    @Autowired
    private UserService userService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            if (user.getAttempts() != null && user.getAttempts() > 0) {
                user.setAttempts(0);
                userService.update(user, user.getId());
            }
        } catch (FeignException e) {
            LOG.error("The user [{}] does not exist", authentication.getName(), e);
        }

        LOG.info("Success Login: [{}]", userDetails.getUsername());
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        LOG.info("Success Failed: [{}]", authentication.getName());

        try {
            User user = userService.findByUsername(authentication.getName());
            if (user.getAttempts() == null) {
                user.setAttempts(0);
            }

            LOG.info("The user [{}] had [{}] tries before the current one", user.getUsername(), user.getAttempts());
            user.setAttempts(user.getAttempts() + 1);

            if (user.getAttempts() >= 3) {
                LOG.info("The user [{}] has been disabled for maximum number of tries", user.getUsername());
                user.setEnabled(false);
            }

            tracer.currentSpan().tag("error.attempts", "The user has " + user.getAttempts() + " failure attempts");
            userService.update(user, user.getId());

        } catch (FeignException e) {
            LOG.error("The user [{}] does not exist", authentication.getName(), e);
        }

    }
}
