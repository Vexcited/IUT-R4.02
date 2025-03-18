package iut.java.spring.tests.unit;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.entity.Individu;
import iut.java.spring.repository.IIndividuRepository;
import iut.java.spring.service.impl.IndividuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndividuServiceTest {
    @Mock
    private IIndividuRepository repository;

    @InjectMocks
    private IndividuService service;

    @Test
    void testRemove() {
        // ACT
        service.remove(1L);

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
        Optional<IndividuDto> individuDto = service.get(1L);

        // ASSERT
        assertThat(individuDto).isPresent();
        assertThat(individuDto.get()).extracting(
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
        Optional<IndividuDto> individuDto = service.get(1L);

        // ASSERT
        assertThat(individuDto).isEmpty();
        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }
}
