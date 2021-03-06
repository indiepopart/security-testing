package com.okta.developer.theaters.controller;

import com.okta.developer.theaters.model.Location;
import com.okta.developer.theaters.model.Theater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOpaqueToken;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@SpringBootTest
@AutoConfigureWebTestClient
public class TheaterControllerTest {


    @Autowired
    private WebTestClient client;


    @Test
    public void collectionGet_noAuth_returnsUnauthorized() throws Exception {
        this.client.get().uri("/theater").exchange().expectStatus().isUnauthorized();
    }

    @Test
    public void collectionGet_withValidOpaqueToken_returnsOk() throws Exception {
        this.client.mutateWith(mockOpaqueToken()).get().uri("/theater").exchange().expectStatus().isOk();

    }

    @Test
    public void post_withOpaqueToken_returnsFodbidden() throws Exception{
        Theater theater = new Theater();
        theater.setId("123");
        theater.setLocation(new Location());
        this.client.mutateWith(mockOpaqueToken())
                .post().uri("/theater").body(fromValue(theater))
                .exchange().expectStatus().isForbidden();
    }

    @Test
    public void post_withValidOpaqueToken_returnsCreated() throws Exception{
        Theater theater = new Theater();
        theater.setLocation(new Location());
        this.client.mutateWith(mockOpaqueToken().authorities(new SimpleGrantedAuthority("theater_admin")))
                .post().uri("/theater").body(fromValue(theater))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().jsonPath("$.id").isNotEmpty();
    }
}
