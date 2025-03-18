package iut.java.utils;

import iut.java.dto.IndividuDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe de sérialisation d'un individu en texte et inversement.
 */
public class IndividuUtils {

    /**
     * Connvertit une line CSV en objet IndividuDto
     * @param line Ligne CSV
     * @return Un objet IndividuDto
     */
    public static IndividuDto getIndividu(String line) {
        String[] s = line.split(",");
        IndividuDto i = new IndividuDto();
        i.setId(Long.parseLong(s[0]));
        i.setFirstName(s[1]);
        i.setLastName(s[2]);
        i.setTitle(s[3]);
        i.setHeight(Integer.parseInt(s[4]));
        if (s.length == 6) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            i.setBirthDate(LocalDate.parse(s[5], formatter));
        }
        return i;
    }

    /**
     * Convertit un objet IndividuDTO en ligne CSV
     * @param dto L'IndividuDTO
     * @return La ligne CSV
     */
    public static String getLine(IndividuDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dto.getId() + "," +
                dto.getFirstName() + "," +
                dto.getLastName() + "," +
                dto.getTitle() + "," +
                dto.getHeight() + "," +
                dto.getBirthDate().format(formatter);
    }
}
