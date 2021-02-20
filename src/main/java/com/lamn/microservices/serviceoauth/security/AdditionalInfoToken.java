package com.lamn.microservices.serviceoauth.security;

import com.lamn.microservices.serviceoauth.services.UserService;
import com.lamn.microservices.userscommons.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> tokenInfo = new HashMap<>();
        User user = userService.findByUsername(oAuth2Authentication.getName());

        tokenInfo.put("firstName", user.getFirstName());
        tokenInfo.put("lastName", user.getLastName());
        tokenInfo.put("email", user.getEmail());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(tokenInfo);

        return oAuth2AccessToken;
    }
}
