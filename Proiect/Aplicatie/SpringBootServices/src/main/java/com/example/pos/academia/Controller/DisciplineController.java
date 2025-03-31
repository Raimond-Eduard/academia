package com.example.pos.academia.Controller;

import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.services.DisciplineService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/academia/discipline")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetches all the available courses, with possibility of filtering " +
                            "either by displaying them in pagination or filtering them by type or category"
            )
    })
    public ResponseEntity<?> getAllDiscipline(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "0") int items_per_page,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String category
    )
    {
        String baseLink = linkTo(methodOn(DisciplineController.class).getAllDiscipline(0,0, null, null))
                .toUriComponentsBuilder()
                .replaceQuery(null)
                .build()
                .toString();
        if ((type != null && !type.isEmpty()) && (category != null && !category.isEmpty()))
        {
            List<DisciplineDTO> disciplineDTOList = disciplineService.getDisciplineByTypeAndCategory(type, category);
            List<EntityModel<DisciplineDTO>> models = disciplineService.getDefaultHateoas(disciplineDTOList, baseLink);
            return new ResponseEntity<>(models, HttpStatus.OK);
        }else if((type != null && !type.isEmpty()) || (category != null && !category.isEmpty())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (items_per_page > 0) {
            Page<DisciplineDTO> disciplineDTOPage = disciplineService.getAllDisciplines(page, items_per_page);
            CollectionModel<EntityModel<DisciplineDTO>> models = disciplineService.getPaginatedHateoas(baseLink, page, items_per_page);
            return new ResponseEntity<>(models, HttpStatus.OK);
        }
        Page<DisciplineDTO> disciplineDTOPage = disciplineService.getAllDisciplines(page, 10);
        List<EntityModel<DisciplineDTO>> models = disciplineService.getDefaultHateoas(disciplineDTOPage.stream().toList(), baseLink);
        return new ResponseEntity<>(models, HttpStatus.OK);

    }

    @GetMapping("/{cod}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The course with the specified code exists therefore their data is displayed"
            )
    })
    public ResponseEntity<?> getDisciplineById(@PathVariable String cod) {
        Optional<DisciplineDTO> disciplineDTO = disciplineService.getDisciplineByCod(cod);
        List<EntityModel<DisciplineDTO>> model = disciplineService.getDefaultHateoas(disciplineDTO.stream().toList(), linkTo(methodOn(DisciplineController.class).getAllDiscipline(0, 0, null, null))
                .toUriComponentsBuilder()
                .replaceQuery(null)
                .build()
                .toString());

        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
