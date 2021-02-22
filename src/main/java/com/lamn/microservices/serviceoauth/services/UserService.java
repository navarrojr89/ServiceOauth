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

    /**
     * Update user.
     *
     * @param user the user
     * @param id   the id
     * @return the user
     */
    User update(User user, Long id);

}
