package com.lamn.microservices.serviceoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type Service oauth application.
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ServiceOauthApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceOauthApplication.class, args);
    }

}
