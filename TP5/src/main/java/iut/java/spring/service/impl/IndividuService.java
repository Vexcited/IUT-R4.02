package iut.java.spring.service.impl;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.entity.Individu;
import iut.java.spring.repository.IIndividuRepository;
import iut.java.spring.service.interfaces.IIndividuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndividuService implements IIndividuService {
    @Autowired
    private IIndividuRepository repository;

    @Override
    public List<IndividuDto> getList() {
        return repository.findAll().stream().map(IndividuService::getDto).toList();
    }

    @Override
    public Optional<IndividuDto> get(long id) {
        return repository.findById(id).map(IndividuService::getDto);
    }

    @Override
    public void remove(long id) {
        repository.deleteById(id);
    }

    @Override
    public IndividuDto add(IndividuDto dto) {
        Individu entity = IndividuService.getEntity(dto);
        repository.saveAndFlush(entity);
        return IndividuService.getDto(entity);
    }

    @Override
    public boolean modify(IndividuDto dto) {
        Optional<Individu> o = repository.findById(dto.getId());
        if (o.isEmpty()) {
            return false;
        }
        IndividuService.setEntity(o.get(), dto);
        repository.saveAndFlush(o.get());
        return true;
    }

    /**
     * Convertit une entity en entrée en DTO
     * @param entity Entity à convertir
     * @return DTO instancié à partir d'une entity
     */
    private static IndividuDto getDto(Individu entity) {
        IndividuDto d = new IndividuDto();
        d.setId(entity.getId());
        d.setFirstName(entity.getFirstName());
        d.setLastName(entity.getLastName());
        d.setTitle(entity.getTitle());
        d.setHeight(entity.getHeight());
        d.setBirthDate(entity.getBirthDate());
        return d;
    }

    /**
     * Initialise une entity à partir d'un DTO
     * @param dto DTO à convertir
     * @return Entity instanciée à partir d'un DTO
     */
    public static Individu getEntity(IndividuDto dto) {
        Individu e = new Individu();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setTitle(dto.getTitle());
        e.setHeight(dto.getHeight());
        e.setBirthDate(dto.getBirthDate());
        return e;
    }

    /**
     * Fixe les valeurs de l'entity à partir d'un DTO
     * @param entity Entity dont il faut fixer les valeurs
     * @param dto DTO servant à fixer les valeurs
     */
    public static void setEntity(Individu entity, IndividuDto dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setTitle(dto.getTitle());
        entity.setHeight(dto.getHeight());
        entity.setBirthDate(dto.getBirthDate());
    }
}
