package com.okta.developer.apigateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

@SpringBootTest
@AutoConfigureWebTestClient
public class GreetingControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void greetingGet_noAuth_returnsUnauthorized(){
        this.client.get().uri("/greeting")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    public void greetingGet_withOidcLogin_returnsOk(){
        this.client.mutateWith(mockOidcLogin())
                .get().uri("/greeting")
                .exchange()
                .expectStatus().isOk();
    }
}
