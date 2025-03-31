package com.example.pos.academia.services;


import com.example.pos.academia.Controller.CadreDidacticeController;
import com.example.pos.academia.Controller.DisciplineController;
import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.enums.CategorieDisciplina;
import com.example.pos.academia.enums.TipDisciplina;
import com.example.pos.academia.repository.DisciplineRepository;
import com.example.pos.academia.repository.JoinDSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private JoinDSRepository joinDSRepository;


    public Page<DisciplineDTO> getAllDisciplines(int page, int size){
        return disciplineRepository.findAll(PageRequest.of(page,size))
                .map(DisciplineDTO::new);
    }

    public Optional<DisciplineDTO> getDisciplineByCod(String cod){
        return disciplineRepository.findByCod(cod).map(DisciplineDTO::new);
    }

    public List<DisciplineDTO> getDisciplineByTitular(Long idTitular)
    {
        return disciplineRepository.findByTitularId(idTitular).stream()
                .map(DisciplineDTO::new)
                .collect(Collectors.toList());
    }

    public List<DisciplineDTO> getDisciplineByTypeAndCategory(String type, String category)
    {
        return disciplineRepository.findByTypeAndCategory(type.toLowerCase(),category.toLowerCase())
                .stream()
                .map(DisciplineDTO::new)
                .collect(Collectors.toList());
    }

    public List<EntityModel<DisciplineDTO>> getDefaultHateoas(List<DisciplineDTO> courses, String baseLink) {
        return courses.stream()
                .map(course -> EntityModel.of(course, linkTo(methodOn(DisciplineController.class)
                        .getDisciplineById(course.getCod())).withSelfRel()).add(
                        Link.of(baseLink)
                                .withRel("parent")))
                .toList();
    }

    public CollectionModel<EntityModel<DisciplineDTO>> getPaginatedHateoas(String baseLink, int page, int size) {
        List<DisciplineDTO> allCourses = disciplineRepository.findAll()
                .stream()
                .map(DisciplineDTO::new)
                .collect(Collectors.toList());

        int start = page * size;
        int end = Math.min(start + size, allCourses.size());

        List<EntityModel<DisciplineDTO>> models;

        if(start >= allCourses.size()) {
            return CollectionModel.empty();
        }

        List<DisciplineDTO> paged = allCourses.subList(start, end);
        models = paged.stream()
                .map(course -> EntityModel.of(course,
                        linkTo(methodOn(DisciplineController.class).getDisciplineById(course.getCod()))
                                .withSelfRel())).toList();

        CollectionModel<EntityModel<DisciplineDTO>> collectionModel = CollectionModel.of(models);

        collectionModel.add(Link.of(baseLink).withRel("parent"));

        if(end <  allCourses.size()) {
            collectionModel.add(
                    linkTo(methodOn(DisciplineController.class)
                            .getAllDiscipline(0, 0, null, null))
                            .withRel("next"));
        }
        if (start > 0) {
            collectionModel.add(
                    linkTo(methodOn(DisciplineController.class)
                            .getAllDiscipline(0,0, null, null))
                            .withRel("prev"));
        }
        collectionModel.add(
                linkTo(methodOn(DisciplineController.class)
                        .getAllDiscipline(0,0, null, null))
                        .withSelfRel());

        return collectionModel;
    }

    public boolean existsById(String cod){
        return disciplineRepository.existsByCod(cod);
    }
}
