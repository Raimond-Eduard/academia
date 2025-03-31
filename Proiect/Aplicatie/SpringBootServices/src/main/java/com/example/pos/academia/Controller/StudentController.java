package com.example.pos.academia.Controller;


import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.dto.StudentDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.services.DisciplineService;
import com.example.pos.academia.services.JoinDSService;
import com.example.pos.academia.services.StudentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private JoinDSService joinDSService;
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "Gets all the available students and can display them with the possibility to pagination" +
                    " plus full name matching search and partial name matching search"
    )
    public ResponseEntity<?> getAllStudents(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "items_per_page", defaultValue = "10", required = false) int itemsPerPage,
            @RequestParam(value = "name", required = false, defaultValue = "?") String nume) {
        String baseLink = linkTo(methodOn(StudentController.class).getAllStudents(0,0, null))
                .toUriComponentsBuilder()
                .replaceQuery(null)
                .build()
                .toString();

        if (!Objects.equals(nume, "?") && nume != null)
        {
            List<StudentDTO> studentsByNume = studentService.getStudentsByNume(nume);
            List<StudentDTO> studentByPrenume = studentService.getStudentsByprenume(nume);
            List<EntityModel<StudentDTO>> students;
            if(studentsByNume.isEmpty() && studentByPrenume.isEmpty()) {

                studentsByNume = studentService.getStudentsByPrefix(nume);

            }

            if(studentsByNume.isEmpty()) {

                students = studentService.getDefaultModel(studentByPrenume, baseLink);

                return new ResponseEntity<>(students, HttpStatus.OK);
            }
            students = studentService.getDefaultModel(studentByPrenume, baseLink);

            return new ResponseEntity<>(students, HttpStatus.OK);

        }

        if(itemsPerPage > 0)
        {
            CollectionModel<EntityModel<StudentDTO>> models = studentService.getPaginatedModel(baseLink, page, itemsPerPage);
            return new ResponseEntity<>(models, HttpStatus.OK);
        }

        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The student with the specified id is found!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = StudentDTO.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The student with the specified id either never existed or was deleted in the process!"
            )
    })
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<StudentDTO> studentDTO = studentService.getStudentById(id);
        EntityModel<StudentDTO> model;
        if (studentDTO.isPresent()) {
            model = EntityModel.of(studentDTO.get(),
                    linkTo(methodOn(StudentController.class).getStudentById(studentDTO.get().getId())).withSelfRel())
                    .add(Link.of(linkTo(methodOn(StudentController.class).getAllStudents(0,0,null))
                            .toUriComponentsBuilder()
                            .replaceQuery(null)
                            .build().toString()).withRel("parent")
            );
            long nextId = studentDTO.get().getId() + 1;
            long prevId = studentDTO.get().getId() - 1;

            if(studentService.studentExistsById(prevId)){
                model.add(linkTo(methodOn(StudentController.class).getStudentById(prevId)).withRel("prev"));
            }

            if(studentService.studentExistsById(nextId)){
                model.add(linkTo(methodOn(StudentController.class).getStudentById(nextId)).withRel("next"));
            }

            return new ResponseEntity<>(model, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}/discipline")
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified id exists and has been enrolled to the shown disciplines"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified id either doesn\'t exist or it\'s not enrolled to any discipline"
            )}
    )
    public ResponseEntity<?> getDisciplineStudent(@PathVariable Long id)
    {
        List<String> courseCodes = joinDSService.getDisciplineCodes(id);
        List<DisciplineDTO> disciplineDTOList = new ArrayList<>();
        for (String courseCode : courseCodes) {
            disciplineDTOList.add(disciplineService.getDisciplineByCod(courseCode).get());
        }
        return new ResponseEntity<>(disciplineDTOList, HttpStatus.OK);
    }
}
