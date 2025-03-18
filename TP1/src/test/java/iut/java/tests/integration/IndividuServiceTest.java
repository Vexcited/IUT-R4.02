package iut.java.tests.integration;

import iut.java.dto.IndividuDto;
import iut.java.service.IndividuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
        int count = list.size();

        // ASSERT
        Assertions.assertEquals(50, count);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ',', value = {
        "0,Meggy,Vain",
        "1,Nicolas,Leghorn",
        "2,Conny,Laying"
    })
    void testLoadedIndividusName(int index, String firstName, String lastName) {
        // ACT
        List<IndividuDto> list = service.getIndividusList();
        IndividuDto individu = list.get(index);

        // ASSERT
        Assertions.assertEquals(firstName, individu.getFirstName());
        Assertions.assertEquals(lastName, individu.getLastName());
    }
}
