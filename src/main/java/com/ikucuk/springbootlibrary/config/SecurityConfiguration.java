package com.ikucuk.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // Disable Cross Site Request Forgery
        http.csrf(AbstractHttpConfigurer::disable);

        // Protect endpoints at /api/<type>/secure
               http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/api/books/secure/**",
                                        "/api/reviews/secure/**")
                                .authenticated().anyRequest().permitAll())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

        // Add CORS filters
        http.cors(Customizer.withDefaults());

        // Add content negotiation strategy
        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401's to make the response friendly
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}
/*
1. @Configuration Annotation:

Indicates that this class provides configuration for the application.


2. @Bean Annotation on SecurityFilterChain Method:

Defines a SecurityFilterChain bean, which is a chain of security filters that handle authentication and authorization.

This method configures security rules using the HttpSecurity object.

3. Security Configuration with HttpSecurity:

http.csrf(AbstractHttpConfigurer::disable): Disables Cross-Site Request Forgery (CSRF) protection.

.authorizeHttpRequests(configurer -> ...): Configures authorization rules for specific endpoints.Endpoints under "/api/books/secure/", "/api/reviews/secure/", "/api/messages/secure/", and "/api/admin/secure/" require authentication (authenticated()), while any other request is permitted (anyRequest().permitAll()).

.oauth2ResourceServer(oauth2ResourceServer -> ...): Configures OAuth2 resource server settings..jwt(Customizer.withDefaults()): Specifies that JSON Web Token (JWT) should be used for authentication, and applies default configurations.

http.cors(Customizer.withDefaults()): Adds default Cross-Origin Resource Sharing (CORS) configuration.

http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy()): Sets a content negotiation strategy based on request headers.

Okta.configureResourceServer401ResponseBody(http): Configures Okta-specific settings for handling 401 (Unauthorized) responses.

4. @Configuration and @Bean Annotation Usage:

The SecurityConfiguration class is annotated with @Configuration, indicating that it provides configuration for the Spring application.

The filterChain method annotated with @Bean defines a SecurityFilterChain bean, which is a key part of Spring Security configuration.

Overall, this configuration sets up security rules, authentication, and authorization settings for specific endpoints in the application, leveraging Okta for OAuth2 and JWT-based authentication. It also includes configurations for CORS and content negotiation.
*/