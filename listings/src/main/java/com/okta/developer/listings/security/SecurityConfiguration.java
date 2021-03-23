package com.okta.developer.listings.security;


import com.okta.spring.boot.oauth.Okta;
import com.okta.spring.boot.oauth.config.OktaOAuth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private OktaOAuth2Properties oktaOAuth2Properties;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer().jwt()
                .authenticationManager(customAuthenticationManager());


        Okta.configureResourceServer401ResponseBody(http);
    }



    AuthenticationManager customAuthenticationManager() {
        // this is the keys endpoint for okta
        String issuer = oktaOAuth2Properties.getIssuer();
        String jwkSetUri = issuer + "/v1/keys";

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        // okta recommends validating the `iss` and `aud` claims
        // see: https://developer.okta.com/docs/guides/validate-access-tokens/java/overview/
        List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
        validators.add(new JwtTimestampValidator());
        // Add validation of the issuer claim
        validators.add(new JwtIssuerValidator(issuer));
        validators.add(new AudienceValidator(oktaOAuth2Properties.getAudience()));
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(validators);
        jwtDecoder.setJwtValidator(validator);

        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        authenticationProvider.setJwtAuthenticationConverter(new JwtBearerTokenAuthenticationConverter());
        return authenticationProvider::authenticate;
    }

}
