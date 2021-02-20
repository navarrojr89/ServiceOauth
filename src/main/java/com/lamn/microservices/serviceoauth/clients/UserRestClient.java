package com.lamn.microservices.serviceoauth.clients;

import com.lamn.microservices.userscommons.models.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface User rest client.
 */
@FeignClient(name = "lanm-service-users")
public interface UserRestClient {

    /**
     * Find by username user.
     *
     * @param username the username
     * @return the user
     */
    @GetMapping("/users/search/find-username")
    User findByUsername(@RequestParam String username);

}
