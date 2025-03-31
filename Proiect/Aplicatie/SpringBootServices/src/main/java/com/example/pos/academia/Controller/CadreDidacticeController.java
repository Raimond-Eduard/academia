package com.example.pos.academia.Controller;


import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.enums.GradDidactic;
import com.example.pos.academia.services.CadreDidacticeService;
import com.example.pos.academia.services.DisciplineService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/cadre_didactice")
public class CadreDidacticeController {
    @Autowired
    private CadreDidacticeService cadreDidacticeService;

    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "Gets all the available teachers and has the possibility to display them with " +
                    "either pagination, either to filter them by academic rank, or to find them by name(full match and partial match)"
    )
    public ResponseEntity<?> getAllCadreDidactice(
            @RequestParam(value = "acad_rank", required = false) String acad_rank,
            @RequestParam(value = "name", required = false) String nume,
            @RequestParam(value = "page", required = false, defaultValue = "10") int page,
            @RequestParam(value = "items_per_page", defaultValue = "0")int itemsPerPage
    ) {
        String baseLink = linkTo(methodOn(CadreDidacticeController.class).getAllCadreDidactice(null,null,0,0))
                .toUriComponentsBuilder()
                .replaceQuery(null)
                .build()
                .toString();
        if (nume != null)
        {
            List<CadreDidacticeDTO> cadreDidacticeByNume = cadreDidacticeService.getCadreDidacticeByNume(nume);
            List<CadreDidacticeDTO> cadreDidacticeByPrenume = cadreDidacticeService.getCadreDidacticeByPrenume(nume);
            List<EntityModel<CadreDidacticeDTO>> models;

            if(cadreDidacticeByNume.isEmpty() && cadreDidacticeByPrenume.isEmpty())
            {
                cadreDidacticeByNume = cadreDidacticeService.getCadreDidacticeByPrefixNume(nume);
                models = cadreDidacticeService.getDefaultHateoas(cadreDidacticeByNume, baseLink);
            }
            if(cadreDidacticeByNume.isEmpty())
            {
                models = cadreDidacticeService.getDefaultHateoas(cadreDidacticeByPrenume, baseLink);
                return new ResponseEntity<>(models, HttpStatus.OK);
            }

            models = cadreDidacticeService.getDefaultHateoas(cadreDidacticeByNume, baseLink);

            return new ResponseEntity<>(models, HttpStatus.OK);

        }
        if(acad_rank != null)
        {
            List<CadreDidacticeDTO> cadreDidacticeDTOList = cadreDidacticeService.getCadreDidacticeByGradDidactic(GradDidactic.valueOf(acad_rank.toLowerCase()));

            return new ResponseEntity<>(cadreDidacticeService.getDefaultHateoas(cadreDidacticeDTOList, baseLink), HttpStatus.OK);

        }

        if (itemsPerPage > 0)
        {
            CollectionModel<EntityModel<CadreDidacticeDTO>> models = cadreDidacticeService.getPaginatedHateoas(baseLink, page, itemsPerPage);
            return new ResponseEntity<>(models, HttpStatus.OK);
        }

        List<CadreDidacticeDTO> cadreDidacticeDTOList = cadreDidacticeService.getAllCadreDidactice();
        return new ResponseEntity<>(cadreDidacticeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}/discipline")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The courses taught by the specified id teacher will be displayed alongside the teachers information."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Either the specified id is not found or the teacher is not enrolled in any course"
            )
    })
    public ResponseEntity<?> getDisciplineByIdTitular(@PathVariable Long id) {

        List<DisciplineDTO> disciplineDTOList = disciplineService.getDisciplineByTitular(id);

        if(disciplineDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Something went wrong and could not find disciplines for the given id" + id);
        }
        return new ResponseEntity<>(disciplineDTOList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The specified id exists and as a result the data is being displayed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The specified id no longer exists"
            )
    })
    public ResponseEntity<?> getCadreDidactice(@PathVariable Long id) {
        Optional<CadreDidacticeDTO> cadreDidacticeDTO = cadreDidacticeService.getCadreDidactice(id);
        EntityModel<CadreDidacticeDTO> model = null;
        if (cadreDidacticeDTO.isPresent()) {
            model = EntityModel.of(cadreDidacticeDTO.get(),
                            linkTo(methodOn(CadreDidacticeController.class)
                                    .getCadreDidactice(cadreDidacticeDTO.get().getId()))
                                    .withSelfRel())
                    .add(Link.of(linkTo(methodOn(CadreDidacticeController.class)
                            .getAllCadreDidactice(null, null, 0, 0))
                            .toUriComponentsBuilder()
                            .replaceQuery(null)
                            .build().toString()).withRel("parent"));


            long nextId = cadreDidacticeDTO.get().getId() + 1;
            long previousId = cadreDidacticeDTO.get().getId() - 1;

            if (cadreDidacticeService.existsById(previousId)) {
                model.add(linkTo(methodOn(CadreDidacticeController.class)
                        .getCadreDidactice(previousId)).withRel("prev"));
            }

            if (cadreDidacticeService.existsById(nextId)) {
                model.add(linkTo(methodOn(CadreDidacticeController.class)
                        .getCadreDidactice(nextId)).withRel("next"));
            }

            return new ResponseEntity<>(model, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
