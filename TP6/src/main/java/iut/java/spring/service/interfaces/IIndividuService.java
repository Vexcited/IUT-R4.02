package iut.java.spring.service.interfaces;

import iut.java.spring.dto.IndividuDto;

import java.util.List;
import java.util.Optional;

public interface IIndividuService {

    /**
     * Retourne la liste des individus.
     * @return Liste des individus
     */
    List<IndividuDto> getList();

    /**
     * Retourne l'individu correspondant à l'identifiant
     * @param id Identifiant de l'individu à trouver
     * @return L'Optional est vide (IsEmpty) si l'individu n'a pas été trouvé
     */
    Optional<IndividuDto> get(long id);

    /**
     * Supprime l'individu correspondant à l'identifiant
     * @param id Identifiant de l'individu à trouver
     */
    void remove(long id);

    /**
     * Ajoute l'identifiant et retourne le nouvel individu avec son identifiant
     * @param dto DTO représentant le nouvel individu (comme c'est une création, l'identifiant n'est pas renseigné)
     * @return Le nouvel individu avec son identifiant
     */
    IndividuDto add(IndividuDto dto);

    /**
     * Modifie un individu existant
     * @param dto DTO représentant l'individu
     * @return True si l'individu a été trouvé et False sinon
     */
    boolean modify(IndividuDto dto);
}
