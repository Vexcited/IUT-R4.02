package iut.java.spring.tests.functional;

import iut.java.spring.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class HelloControllerTest {
    @Autowired
    private WebTestClient client;

    @Test
    void testHello() {
        // ACT
        MessageDto message = client.get().uri("/hello/Mikkel")
            .exchange()
            .expectStatus().isOk()
            .expectBody(MessageDto.class)
            .returnResult().getResponseBody();

        // ASSERT
        assertThat(message).extracting(MessageDto::getText).isEqualTo("Hello Mikkel !");
    }
}
