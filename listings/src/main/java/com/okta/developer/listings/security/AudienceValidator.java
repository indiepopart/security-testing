package com.okta.developer.listings.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {


    private String audience;

    public AudienceValidator(String audience) {
        this.audience = audience;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        Set<String> expectedAudience = new HashSet<>();
        // Add validation of the audience claim
        expectedAudience.add(audience);
        // For new Okta orgs, the default audience is `api://default`,
        // if you have changed this from the default update this value
        return !Collections.disjoint(token.getAudience(), expectedAudience)
                ? OAuth2TokenValidatorResult.success()
                : OAuth2TokenValidatorResult.failure(new OAuth2Error(
                OAuth2ErrorCodes.INVALID_REQUEST,
                "This aud claim is not equal to the configured audience",
                "https://tools.ietf.org/html/rfc6750#section-3.1"));    }
}
