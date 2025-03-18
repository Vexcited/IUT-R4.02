package iut.java.tests.unit;

import iut.java.dto.IndividuDto;
import iut.java.utils.IndividuUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class IndividuUtilsTest {
    @Test
    void testGetIndividu() {
        // ARRANGE
        String line = "1,Meggy,Vain,mvain0@vkontakte.ru,Female,14.171.65.1";

        // ACT
        IndividuDto individu = IndividuUtils.getIndividu(line);
        long id = individu.getId();
        String firstName = individu.getFirstName();
        String lastName = individu.getLastName();
        String email = individu.getEmail();
        String gender = individu.getGender();
        String ipAddress = individu.getIpAddress();

        // ASSERT
        Assertions.assertEquals(1L, id);
        Assertions.assertEquals("Meggy", firstName);
        Assertions.assertEquals("Vain", lastName);
        Assertions.assertEquals("mvain0@vkontakte.ru", email);
        Assertions.assertEquals("Female", gender);
        Assertions.assertEquals("14.171.65.1", ipAddress);
    }

    @Test
    void testGetLine() {
        // ARRANGE
        IndividuDto individu = new IndividuDto();
        individu.setId(1L);
        individu.setFirstName("Meggy");
        individu.setLastName("Vain");
        individu.setEmail("mvain0@vkontakte.ru");
        individu.setGender("Female");
        individu.setIpAddress("14.171.65.1");

        // ACT
        String line = IndividuUtils.getLine(individu);

        // ASSERT
        Assertions.assertEquals("1,Meggy,Vain,mvain0@vkontakte.ru,Female,14.171.65.1", line);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "1,Meggy,Vain,mvain0@vkontakte.ru,Female,14.171.65.1|1|Meggy|Vain|mvain0@vkontakte.ru|Female|14.171.65.1",
        "2,Nicolas,Leghorn,nleghorn1@businessweek.com,Male,56.144.102.185|2|Nicolas|Leghorn|nleghorn1@businessweek.com|Male|56.144.102.185",
        "3,Conny,Laying,claying2@bloglovin.com,Male,3.143.77.208|3|Conny|Laying|claying2@bloglovin.com|Male|3.143.77.208"
    })
    void testGetIndividuParam(String line, Long id, String firstName, String lastName, String email, String gender, String ipAddress) {
        // ACT
        IndividuDto individu = IndividuUtils.getIndividu(line);

        // ASSERT
        Assertions.assertEquals(id, individu.getId());
        Assertions.assertEquals(firstName, individu.getFirstName());
        Assertions.assertEquals(lastName, individu.getLastName());
        Assertions.assertEquals(email, individu.getEmail());
        Assertions.assertEquals(gender, individu.getGender());
        Assertions.assertEquals(ipAddress, individu.getIpAddress());
    }

    @ParameterizedTest
    @MethodSource("getLineParam")
    void testGetLineParam (IndividuDto individu, String expected) {
        // ACT
        String line = IndividuUtils.getLine(individu);

        // ASSERT
        Assertions.assertEquals(expected, line);
    }

    static Stream<Arguments> getLineParam() {
        IndividuDto individu1 = new IndividuDto();
        individu1.setId(1L);
        individu1.setFirstName("Meggy");
        individu1.setLastName("Vain");
        individu1.setEmail("mvain0@vkontakte.ru");
        individu1.setGender("Female");
        individu1.setIpAddress("14.171.65.1");

        IndividuDto individu2 = new IndividuDto();
        individu2.setId(2L);
        individu2.setFirstName("Nicolas");
        individu2.setLastName("Leghorn");
        individu2.setEmail("nleghorn1@businessweek.com");
        individu2.setGender("Male");
        individu2.setIpAddress("56.144.102.185");

        IndividuDto individu3 = new IndividuDto();
        individu3.setId(3L);
        individu3.setFirstName("Conny");
        individu3.setLastName("Laying");
        individu3.setEmail("claying2@bloglovin.com");
        individu3.setGender("Male");
        individu3.setIpAddress("3.143.77.208");

        return Stream.of(
            Arguments.arguments(individu1, "1,Meggy,Vain,mvain0@vkontakte.ru,Female,14.171.65.1"),
            Arguments.arguments(individu2, "2,Nicolas,Leghorn,nleghorn1@businessweek.com,Male,56.144.102.185"),
            Arguments.arguments(individu3, "3,Conny,Laying,claying2@bloglovin.com,Male,3.143.77.208")
        );
    }
}
