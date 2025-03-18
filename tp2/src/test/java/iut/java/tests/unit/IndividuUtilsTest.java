package iut.java.tests.unit;

import iut.java.dto.IndividuDto;
import iut.java.utils.IndividuUtils;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.*;

class IndividuUtilsTest {
    /**
     * Vérification de la méthode getLine d'IndividuUtils.
     */
    @Test
    void testGetLine() {
        // ARRANGE
        IndividuDto individu = new IndividuDto() {{
            setId(1L);
            setFirstName("Waring");
            setLastName("Avory");
            setTitle("Rev");
            setHeight(153);
            setBirthDate(LocalDate.of(2024, 4, 17));
        }};

        // ACT
        String line = IndividuUtils.getLine(individu);

        // ASSERT
        assertThat(line).isEqualTo("1,Waring,Avory,Rev,153,17/04/2024");
    }

    /**
     * Vérification de la méthode `getIndividu` d'IndividuUtils.
     * Pour faire celà, on compare les propriétés d'IndividuDto
     * avec `usingRecursiveComparison` de AssertJ.
     */
    @Test
    void testGetIndividu() {
        // ARRANGE
        String line = "1,Waring,Avory,Rev,153,17/04/2024";

        // ACT
        IndividuDto individu = IndividuUtils.getIndividu(line);

        // ASSERT
        assertThat(individu).usingRecursiveComparison().isEqualTo(new IndividuDto() {{
            setId(1L);
            setFirstName("Waring");
            setLastName("Avory");
            setTitle("Rev");
            setHeight(153);
            setBirthDate(LocalDate.of(2024, 4, 17));
        }});
    }
}
