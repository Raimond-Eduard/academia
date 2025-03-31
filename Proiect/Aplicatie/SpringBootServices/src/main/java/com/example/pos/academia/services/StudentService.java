package com.example.pos.academia.services;

import com.example.pos.academia.Controller.StudentController;
import com.example.pos.academia.dto.StudentDTO;
import com.example.pos.academia.entity.Student;
import com.example.pos.academia.enums.CicluStudii;
import com.example.pos.academia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id).map(StudentDTO::new);
    }

    public boolean studentExistsById(Long id) {
        return studentRepository.existsById(id);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id).map(existingStudent ->
        {
            existingStudent.setNume(studentDTO.getNume());
            existingStudent.setPrenume(studentDTO.getPrenume());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setGrupa(studentDTO.getGrupa());
            existingStudent.setAnStudiu(studentDTO.getAnStudiu());
            existingStudent.setCicluStudii(CicluStudii.valueOf(studentDTO.getCicluStudii()));
            return new StudentDTO(existingStudent);
        });
    }

    public List<StudentDTO> getStudentsByPrefix(String prefix) {
        return studentRepository.findByPrefixNume(prefix)
                .stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsByNume(String nume) {
        return studentRepository.findByNume(nume)
                .stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsByprenume(String prenume) {
        return studentRepository.findByPrenume(prenume)
                .stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public List<EntityModel<StudentDTO>> getDefaultModel(List<StudentDTO> students, String baseLink) {
        List<EntityModel<StudentDTO>> models;
        models = students.stream()
                .map(student -> EntityModel.of(student, linkTo(methodOn(StudentController.class)
                        .getStudentById(student.getId())).withSelfRel()).add(Link.of(baseLink).withRel("parent")))
                .toList();
        return models;
    }

    public CollectionModel<EntityModel<StudentDTO>> getPaginatedModel(String baseLink, int page, int size) {
        List<StudentDTO> allStudents = getAllStudents();

        int start = page * size;
        int end = Math.min(start + size, allStudents.size());

        List<EntityModel<StudentDTO>> models;

        if(start >= allStudents.size()) {
            return CollectionModel.empty();
        }

        List<StudentDTO> paginatedStudents = allStudents.subList(start, end);
        models = paginatedStudents.stream()
                .map(student -> EntityModel.of(student,
                        linkTo(methodOn(StudentController.class).getStudentById(student.getId())).withSelfRel()
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<StudentDTO>> collectionModel = CollectionModel.of(models);

        collectionModel.add(Link.of(baseLink).withRel("parent"));

        if(end <  allStudents.size()) {
            collectionModel.add(
                    linkTo(methodOn(StudentController.class)
                            .getAllStudents(page + 1, size, null))
                            .withRel("next"));
        }
        if (start > 0) {
            collectionModel.add(
                    linkTo(methodOn(StudentController.class)
                            .getAllStudents(page - 1, size, null))
                            .withRel("prev"));
        }
        collectionModel.add(
                linkTo(methodOn(StudentController.class)
                        .getAllStudents(page, size, null))
                        .withSelfRel());

        return collectionModel;
    }
}