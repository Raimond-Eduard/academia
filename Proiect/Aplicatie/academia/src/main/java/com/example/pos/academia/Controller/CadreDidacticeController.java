package com.example.pos.academia.Controller;


import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.enums.GradDidactic;
import com.example.pos.academia.services.CadreDidacticeService;
import com.example.pos.academia.services.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/academia/cadre_didactice")
public class CadreDidacticeController {
    @Autowired
    private CadreDidacticeService cadreDidacticeService;

    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
    public ResponseEntity<?> getAllCadreDidactice(
            @RequestParam(value = "acad_rank", required = false) String acad_rank,
            @RequestParam(value = "name", required = false) String nume
    ) {
        if (nume != null)
        {
            List<CadreDidacticeDTO> cadreDidacticeByNume = cadreDidacticeService.getCadreDidacticeByNume(nume);
            List<CadreDidacticeDTO> cadreDidacticeByPrenume = cadreDidacticeService.getCadreDidacticeByPrenume(nume);

            if(cadreDidacticeByNume.isEmpty() && cadreDidacticeByPrenume.isEmpty())
            {
                cadreDidacticeByNume = cadreDidacticeService.getCadreDidacticeByPrefixNume(nume);
            }
            if(cadreDidacticeByNume.isEmpty())
            {
                return new ResponseEntity<>(cadreDidacticeByPrenume, HttpStatus.OK);
            }

            return new ResponseEntity<>(cadreDidacticeByNume, HttpStatus.OK);

        }
        if(acad_rank != null)
        {
            List<CadreDidacticeDTO> cadreDidacticeDTOList = cadreDidacticeService.getCadreDidacticeByGradDidactic(GradDidactic.valueOf(acad_rank.toLowerCase()));
            return new ResponseEntity<>(cadreDidacticeDTOList, HttpStatus.OK);

        }


        List<CadreDidacticeDTO> cadreDidacticeDTOList = cadreDidacticeService.getAllCadreDidactice();
        return new ResponseEntity<>(cadreDidacticeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}/discipline")
    public ResponseEntity<?> getDisciplineByIdTitular(@PathVariable Long id) {

        List<DisciplineDTO> disciplineDTOList = disciplineService.getDisciplineByTitular(id);

        if(disciplineDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Something went wrong and could not find disciplines for the given id" + id);
        }
        return new ResponseEntity<>(disciplineDTOList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCadreDidactice(@PathVariable Long id) {
        return cadreDidacticeService.getCadreDidactice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }



}
