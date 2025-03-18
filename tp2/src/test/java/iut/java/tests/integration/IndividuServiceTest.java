package iut.java.tests.integration;

import iut.java.dto.IndividuDto;
import iut.java.service.IndividuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class IndividuServiceTest {
    private IndividuService service;

    @BeforeEach
    void before() {
        service = new IndividuService("ALMONTE--RINGAUD_Mikkel.csv");
    }

    @Test
    void testCountLoadedIndividus() {
        // ACT
        List<IndividuDto> list = service.getIndividusList();

        // ASSERT
        assertThat(list).hasSize(50);
    }

    @Test
    void testLoadedIndividusName() {
        // ACT
        List<IndividuDto> list = service.getIndividusList();

        // ASSERT
        assertThat(list).extracting(IndividuDto::getFirstName, IndividuDto::getLastName)
            .startsWith(
                tuple("Glennie", "Eburah"),
                tuple("Artemas", "Schult"),
                tuple("Leroi", "Cotty"),
                tuple("Vonnie", "Pollak"),
                tuple("Horace", "Goodding")
            );
    }

    @Test
    void testLoadedIndividusHeight() {
        // ACT
        List<IndividuDto> list = service.getIndividusList();

        // ASSERT
        assertThat(list).allMatch(i -> i.getHeight() >= 150 && i.getHeight() <= 200);
    }

    @Test
    void testLoadedIndividusTitle() {
        // ACT
        List<IndividuDto> list = service.getIndividusList();

        // ASSERT
        assertThat(list).extracting(IndividuDto::getTitle)
            .containsOnly("Rev", "Dr", "Mr", "Mrs", "Ms", "Honorable");
    }

    @Test
    void testLoadedIndividusBirthDate() {
        // ACT
        List<IndividuDto> list = service.getIndividusList();

        // ASSERTS

        // Le nombre d’individus avec la date de naissance qui est « null »
        assertThat(list).filteredOn(i -> i.getBirthDate() == null).hasSize(2);

        // Le nombre d’individus avec la date de naissance qui antérieure au « 01/01/2000 »
        assertThat(list).filteredOn(i -> i.getBirthDate() != null && i.getBirthDate().isBefore(LocalDate.of(2000, 1, 1))).hasSize(35);

        // Le nombre d’individus avec la date de naissance qui postérieure au « 01/01/2000 »
        assertThat(list).filteredOn(i -> i.getBirthDate() != null && i.getBirthDate().isAfter(LocalDate.of(2000, 1, 1))).hasSize(13);
    }

    @Test
    void testLoadedIndividusTitleRepartition() {
        // ACT
        Map<String, Long> titleRepartition = service.getTitleRepartition();

        assertThat(titleRepartition).containsOnly(
            entry("Dr", 10L),
            entry("Honorable", 4L),
            entry("Mr", 10L),
            entry("Mrs", 8L),
            entry("Ms", 13L),
            entry("Rev", 5L)
        );
    }
}
