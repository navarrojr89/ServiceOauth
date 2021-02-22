package com.lamn.microservices.serviceoauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LOG.info("Success Login: [{}]", userDetails.getUsername());
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        LOG.info("Success Failed: [{}]", exception.getMessage());
    }
}
