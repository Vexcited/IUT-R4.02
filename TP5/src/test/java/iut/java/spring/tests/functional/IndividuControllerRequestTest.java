package iut.java.spring.tests.functional;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import iut.java.spring.dto.IndividuDto;
import org.assertj.db.type.DateValue;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.sql.DataSource;
import java.time.LocalDate;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.db.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@TestPropertySource(locations = { "classpath:application.h2.test.properties" })
class IndividuControllerRequestTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    private DataSource source;

    private Request request;

    @BeforeEach
    void each() {
        // ARRANGE
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(source),
            sequenceOf(deleteAllFrom("individu"),
                insertInto("individu").columns("id", "first_name", "last_name", "title", "height", "birth_date")
                    .values(1L, "Rikki", "Scourge", "Honorable", 175, LocalDate.of(1996, 9, 20))
                    .values(2L, "Papageno", "Beastall", "Mr", 198, LocalDate.of(1993, 12, 11))
                    .values(3L, "Hakeem", "Saph", "Dr", 153, LocalDate.of(1990, 5, 12))
                    .values(4L, "Dillon", "Tytler", "Honorable", 181, LocalDate.of(1990, 8, 10))
                    .values(5L, "Shani", "Lantuff", "Mr", 163, LocalDate.of(1983, 5, 14))
                    .values(6L, "Casey", "Oswald", "Dr", 166, LocalDate.of(1974, 6, 13))
                    .values(7L, "Tyrus", "Bailess", "Honorable", 195, LocalDate.of(1970, 1, 19))
                    .values(8L, "Welbie", "Dalli", "Mrs", 167, LocalDate.of(2006, 6, 9))
                    .values(9L, "Stacia", "Eason", "Mr", 175, LocalDate.of(2005, 6, 9))
                    .values(10L, "Klement", "Snoxall", "Honorable", 155, LocalDate.of(1992, 2, 1))
                    .build()
            ));

        request = new Request(source, "SELECT id, first_name, last_name, title, height, birth_date FROM individu");

        dbSetup.launch();
    }

    @Test
    void testAdd() {
        IndividuDto dto = new IndividuDto() {{
            setFirstName("Rikka");
            setLastName("Takanashi");
            setTitle("Miss");
            setHeight(155);
            setBirthDate(LocalDate.of(1998, 6, 9));
        }};

        IndividuDto dto2 = client.post().uri("/individu")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk()
            .expectBody(IndividuDto.class)
            .returnResult().getResponseBody();

        assertThat(dto2).extracting(
            IndividuDto::getFirstName,
            IndividuDto::getLastName,
            IndividuDto::getHeight,
            IndividuDto::getBirthDate,
            IndividuDto::getTitle
        ).containsExactly(
            "Rikka",
            "Takanashi",
            155,
            LocalDate.of(1998, 6, 9),
            "Miss"
        );

        assert dto2 != null;
        assertThat(request).hasNumberOfRows(11)
            .row().hasValues(1L, "Rikki", "Scourge", "Honorable", 175, DateValue.of(1996, 9, 20))
            .row().hasValues(2L, "Papageno", "Beastall", "Mr", 198, DateValue.of(1993, 12, 11))
            .row().hasValues(3L, "Hakeem", "Saph", "Dr", 153, DateValue.of(1990, 5, 12))
            .row().hasValues(4L, "Dillon", "Tytler", "Honorable", 181, DateValue.of(1990, 8, 10))
            .row().hasValues(5L, "Shani", "Lantuff", "Mr", 163, DateValue.of(1983, 5, 14))
            .row().hasValues(6L, "Casey", "Oswald", "Dr", 166, DateValue.of(1974, 6, 13))
            .row().hasValues(7L, "Tyrus", "Bailess", "Honorable", 195, DateValue.of(1970, 1, 19))
            .row().hasValues(8L, "Welbie", "Dalli", "Mrs", 167, DateValue.of(2006, 6, 9))
            .row().hasValues(9L, "Stacia", "Eason", "Mr", 175, DateValue.of(2005, 6, 9))
            .row().hasValues(10L, "Klement", "Snoxall", "Honorable", 155, DateValue.of(1992, 2, 1))
            .row().hasValues(dto2.getId(), "Rikka", "Takanashi", "Miss", 155, DateValue.of(1998, 6, 9));
    }

    @Test
    void testModifyFound() {
        IndividuDto dto = new IndividuDto() {{
            setId(10L);
            setFirstName("Rikka");
            setLastName("Takanashi");
            setTitle("Miss");
            setHeight(155);
            setBirthDate(LocalDate.of(1998, 6, 9));
        }};

        client.put().uri("/individu")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk();

        assertThat(request).hasNumberOfRows(10)
            .row().hasValues(1L, "Rikki", "Scourge", "Honorable", 175, DateValue.of(1996, 9, 20))
            .row().hasValues(2L, "Papageno", "Beastall", "Mr", 198, DateValue.of(1993, 12, 11))
            .row().hasValues(3L, "Hakeem", "Saph", "Dr", 153, DateValue.of(1990, 5, 12))
            .row().hasValues(4L, "Dillon", "Tytler", "Honorable", 181, DateValue.of(1990, 8, 10))
            .row().hasValues(5L, "Shani", "Lantuff", "Mr", 163, DateValue.of(1983, 5, 14))
            .row().hasValues(6L, "Casey", "Oswald", "Dr", 166, DateValue.of(1974, 6, 13))
            .row().hasValues(7L, "Tyrus", "Bailess", "Honorable", 195, DateValue.of(1970, 1, 19))
            .row().hasValues(8L, "Welbie", "Dalli", "Mrs", 167, DateValue.of(2006, 6, 9))
            .row().hasValues(9L, "Stacia", "Eason", "Mr", 175, DateValue.of(2005, 6, 9))
            .row().hasValues(10L, "Rikka", "Takanashi", "Miss", 155, DateValue.of(1998, 6, 9));
    }

    @Test
    void testModifyNotFound() {
        IndividuDto dto = new IndividuDto() {{
            setId(100L);
            setFirstName("Rikka");
            setLastName("Takanashi");
            setTitle("Miss");
            setHeight(155);
            setBirthDate(LocalDate.of(1998, 6, 9));
        }};

        client.put().uri("/individu")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound();

        assertThat(request).hasNumberOfRows(10)
            .row().hasValues(1L, "Rikki", "Scourge", "Honorable", 175, DateValue.of(1996, 9, 20))
            .row().hasValues(2L, "Papageno", "Beastall", "Mr", 198, DateValue.of(1993, 12, 11))
            .row().hasValues(3L, "Hakeem", "Saph", "Dr", 153, DateValue.of(1990, 5, 12))
            .row().hasValues(4L, "Dillon", "Tytler", "Honorable", 181, DateValue.of(1990, 8, 10))
            .row().hasValues(5L, "Shani", "Lantuff", "Mr", 163, DateValue.of(1983, 5, 14))
            .row().hasValues(6L, "Casey", "Oswald", "Dr", 166, DateValue.of(1974, 6, 13))
            .row().hasValues(7L, "Tyrus", "Bailess", "Honorable", 195, DateValue.of(1970, 1, 19))
            .row().hasValues(8L, "Welbie", "Dalli", "Mrs", 167, DateValue.of(2006, 6, 9))
            .row().hasValues(9L, "Stacia", "Eason", "Mr", 175, DateValue.of(2005, 6, 9))
            .row().hasValues(10L, "Klement", "Snoxall", "Honorable", 155, DateValue.of(1992, 2, 1));
    }
}
