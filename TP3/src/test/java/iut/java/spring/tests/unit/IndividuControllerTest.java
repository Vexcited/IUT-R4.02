package iut.java.spring.tests.unit;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.dto.MessageDto;
import iut.java.spring.entity.Individu;
import iut.java.spring.service.interfaces.IIndividuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IndividuControllerTest {
    @MockBean
    private IIndividuService service;

    @Autowired
    private WebTestClient client;

    @Test
    void testRemove() {
        // ACT
        client.delete().uri("/individu/1")
            .exchange()
            .expectStatus().isOk();

        // ASSERT
        verify(service).remove(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testGetFound() {
        // ARRANGE
        IndividuDto individu = new IndividuDto() {{
            setId(1L);
            setFirstName("Mikkel");
            setLastName("RINGAUD");
            setHeight(175);
            setBirthDate(LocalDate.of(2005, 10, 6));
            setTitle("Mr");
        }};

        when(service.get(1L)).thenReturn(Optional.of(individu));

        // ACT
        IndividuDto individuDto = client.get().uri("/individu/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(IndividuDto.class)
            .returnResult().getResponseBody();

        // ASSERT
        assertThat(individuDto).extracting(
            IndividuDto::getId,
            IndividuDto::getFirstName,
            IndividuDto::getLastName,
            IndividuDto::getHeight,
            IndividuDto::getBirthDate,
            IndividuDto::getTitle
        ).containsExactly(
            1L,
            "Mikkel",
            "RINGAUD",
            175,
            LocalDate.of(2005, 10, 6),
            "Mr"
        );

        verify(service).get(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    void testGetNotFound() {
        // ARRANGE
        when(service.get(1L)).thenReturn(Optional.empty());

        // ACT
        client.get().uri("/individu/1")
            .exchange()
            .expectStatus().isNotFound();

        // ASSERT
        verify(service).get(1L);
        verifyNoMoreInteractions(service);
    }
}
