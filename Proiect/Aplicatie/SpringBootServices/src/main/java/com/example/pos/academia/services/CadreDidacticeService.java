package com.example.pos.academia.services;

import com.example.pos.academia.Controller.CadreDidacticeController;
import com.example.pos.academia.Controller.StudentController;
import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.dto.StudentDTO;
import com.example.pos.academia.enums.GradDidactic;
import com.example.pos.academia.enums.TipAsociere;
import com.example.pos.academia.repository.CadreDidacticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CadreDidacticeService {
    @Autowired
    private CadreDidacticeRepository cadreDidacticeRepository;

    public List<CadreDidacticeDTO> getAllCadreDidactice() {
        return cadreDidacticeRepository.findAll().stream()
                .map(CadreDidacticeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CadreDidacticeDTO> getCadreDidactice(Long id) {
        return cadreDidacticeRepository.findById(id).map(CadreDidacticeDTO::new);
    }

    public Optional<CadreDidacticeDTO> updateCadreDidactice(Long id, CadreDidacticeDTO cadreDidacticeDTO) {
        return cadreDidacticeRepository.findById(id).map(existingCadreDidactice ->
        {
            existingCadreDidactice.setNume(cadreDidacticeDTO.getNume());
            existingCadreDidactice.setPrenume(cadreDidacticeDTO.getPrenume());
            existingCadreDidactice.setEmail(cadreDidacticeDTO.getEmail());
            existingCadreDidactice.setGradDidactic(GradDidactic.valueOf(cadreDidacticeDTO.getGradDidactic()));
            existingCadreDidactice.setTipAsociere(TipAsociere.valueOf(cadreDidacticeDTO.getTipAsociere()));
            existingCadreDidactice.setAfiliere(cadreDidacticeDTO.getAfiliere());
            return new CadreDidacticeDTO(existingCadreDidactice);
        });
    }

    public List<CadreDidacticeDTO> getCadreDidacticeByGradDidactic(GradDidactic gradDidactic) {
        return cadreDidacticeRepository.findByGradDidactic(gradDidactic)
                .stream().map(CadreDidacticeDTO::new)
                .collect(Collectors.toList());
    }

    public List<CadreDidacticeDTO> getCadreDidacticeByNume(String nume)
    {
        return cadreDidacticeRepository.findByNume(nume).stream()
                .map(CadreDidacticeDTO::new)
                .collect(Collectors.toList());
    }
    public List<CadreDidacticeDTO> getCadreDidacticeByPrenume(String prenume)
    {
        return cadreDidacticeRepository.findByPrenume(prenume)
                .stream()
                .map(CadreDidacticeDTO::new)
                .collect(Collectors.toList());
    }

    public List<CadreDidacticeDTO> getCadreDidacticeByPrefixNume(String prefixNume)
    {
        return cadreDidacticeRepository.findByPrefixNume(prefixNume)
                .stream()
                .map(CadreDidacticeDTO::new)
                .collect(Collectors.toList());
    }

    public List<EntityModel<CadreDidacticeDTO>> getDefaultHateoas(List<CadreDidacticeDTO> profesors, String baseLink) {
        return profesors.stream()
                .map(profesor -> EntityModel.of(profesor, linkTo(methodOn(CadreDidacticeController.class)
                        .getDisciplineByIdTitular(profesor.getId())).withSelfRel()).add(
                                Link.of(baseLink)
                                        .withRel("parent")))
                .toList();
    }

    public CollectionModel<EntityModel<CadreDidacticeDTO>> getPaginatedHateoas(String baseLink, int page, int size) {
        List<CadreDidacticeDTO> allProfesori = getAllCadreDidactice();

        int start = page * size;
        int end = Math.min(start + size, allProfesori.size());

        List<EntityModel<CadreDidacticeDTO>> models;

        if(start >= allProfesori.size()) {
            return CollectionModel.empty();
        }

        List<CadreDidacticeDTO> paginatedStudents = allProfesori.subList(start, end);
        models = paginatedStudents.stream()
                .map(profesor -> EntityModel.of(profesor,
                        linkTo(methodOn(CadreDidacticeController.class).getDisciplineByIdTitular(profesor.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CadreDidacticeDTO>> collectionModel = CollectionModel.of(models);

        collectionModel.add(Link.of(baseLink).withRel("parent"));

        if(end <  allProfesori.size()) {
            collectionModel.add(
                    linkTo(methodOn(CadreDidacticeController.class)
                            .getAllCadreDidactice(null, null, page + 1, size))
                            .withRel("next"));
        }
        if (start > 0) {
            collectionModel.add(
                    linkTo(methodOn(CadreDidacticeController.class)
                            .getAllCadreDidactice(null, null,page - 1, size))
                            .withRel("prev"));
        }
        collectionModel.add(
                linkTo(methodOn(CadreDidacticeController.class)
                        .getAllCadreDidactice(null, null, page, size))
                        .withSelfRel());

        return collectionModel;
    }

    public boolean existsById(Long id){
        return cadreDidacticeRepository.existsById(id);
    }

}
