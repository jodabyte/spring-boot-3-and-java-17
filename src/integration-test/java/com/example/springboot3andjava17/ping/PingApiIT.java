package com.example.springboot3andjava17.ping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingApiIT {

    @Autowired
    private WebTestClient webClient;

    @Test
    void Ping_Call_Ok() throws Exception {
        ResponseSpec response = webClient.get().uri("/ping").exchange();
        response.expectStatus().isOk();

        Ping actual = response.expectBody(Ping.class).returnResult().getResponseBody();
        assertThat(actual).hasNoNullFieldsOrProperties();
    }
}
