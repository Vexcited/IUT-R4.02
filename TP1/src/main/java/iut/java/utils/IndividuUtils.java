package iut.java.utils;

import iut.java.dto.IndividuDto;

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
        i.setEmail(s[3]);
        i.setGender(s[4]);
        i.setIpAddress(s[5]);
        return i;
    }

    /**
     * Convertit un objet IndividuDTO en ligne CSV
     * @param dto L'IndividuDTO
     * @return La ligne CSV
     */
    public static String getLine(IndividuDto dto) {
        return dto.getId() + "," +
                dto.getFirstName() + "," +
                dto.getLastName() + "," +
                dto.getEmail() + "," +
                dto.getGender() + "," +
                dto.getIpAddress();
    }
}
