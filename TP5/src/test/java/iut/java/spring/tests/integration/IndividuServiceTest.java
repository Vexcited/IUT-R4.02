package iut.java.spring.tests.integration;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import iut.java.spring.dto.IndividuDto;
import iut.java.spring.service.interfaces.IIndividuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@TestPropertySource(locations = { "classpath:application.h2.test.properties" })
class IndividuServiceTest {
    @Autowired
    private IIndividuService service;

    @Autowired
    private DataSource source;

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

        dbSetup.launch();
    }

    @Test
    void testGetList() {
        // ACT
        List<IndividuDto> list = service.getList();

        // ASSERT
        assertThat(list).extracting(IndividuDto::getFirstName, IndividuDto::getLastName)
            .containsExactly(
                tuple("Rikki", "Scourge"),
                tuple("Papageno", "Beastall"),
                tuple("Hakeem", "Saph"),
                tuple("Dillon", "Tytler"),
                tuple("Shani", "Lantuff"),
                tuple("Casey", "Oswald"),
                tuple("Tyrus", "Bailess"),
                tuple("Welbie", "Dalli"),
                tuple("Stacia", "Eason"),
                tuple("Klement", "Snoxall")
            );
    }
}
