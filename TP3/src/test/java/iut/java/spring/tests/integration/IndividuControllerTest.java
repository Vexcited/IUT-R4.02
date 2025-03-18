package iut.java.spring.tests.integration;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.entity.Individu;
import iut.java.spring.repository.IIndividuRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IndividuControllerTest {
    @MockBean
    private IIndividuRepository repository;

    @Autowired
    private WebTestClient client;

    @Test
    void testRemove() {
        // ACT
        client.delete().uri("/individu/1")
                .exchange()
                .expectStatus().isOk();

        // ASSERT
        verify(repository).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testGetFound() {
        // ARRANGE
        Individu individu = new Individu() {{
            setId(1L);
            setFirstName("Mikkel");
            setLastName("RINGAUD");
            setHeight(175);
            setBirthDate(LocalDate.of(2005, 10, 6));
            setTitle("Mr");
        }};

        when(repository.findById(1L)).thenReturn(Optional.of(individu));

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

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testGetNotFound() {
        // ARRANGE
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // ACT
        client.get().uri("/individu/1")
                .exchange()
                .expectStatus().isNotFound();

        // ASSERT
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }
}
