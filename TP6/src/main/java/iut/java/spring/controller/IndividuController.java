package iut.java.spring.controller;

import iut.java.spring.dto.IndividuDto;
import iut.java.spring.service.interfaces.IIndividuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class IndividuController {
    @Autowired
    private IIndividuService service;

    @GetMapping("/individus")
    public List<IndividuDto> getList() {
        return service.getList();
    }

    @GetMapping("/individu/{id}")
    public IndividuDto get(@PathVariable long id) {
        Optional<IndividuDto> o = service.get(id);
        if (o.isPresent()) {
            return o.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    @DeleteMapping("/individu/{id}")
    public void remove(@PathVariable long id) {
        service.remove(id);
    }

    @PostMapping("/individu")
    public IndividuDto add(@RequestBody IndividuDto dto) {
        return service.add(dto);
    }

    @PutMapping("/individu")
    public void modify(@RequestBody IndividuDto dto) {
        if (!service.modify(dto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
    }
}
