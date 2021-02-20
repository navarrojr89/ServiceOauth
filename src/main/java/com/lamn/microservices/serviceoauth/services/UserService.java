package com.lamn.microservices.serviceoauth.services;

import com.lamn.microservices.userscommons.models.entity.User;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Find by username user.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);

}
