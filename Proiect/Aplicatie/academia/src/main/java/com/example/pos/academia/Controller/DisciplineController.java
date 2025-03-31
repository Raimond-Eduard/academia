package com.example.pos.academia.Controller;

import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.services.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academia/discipline")
public class DisciplineController {
    @Autowired
    private DisciplineService disciplineService;

    @GetMapping
    public ResponseEntity<?> getAllDiscipline(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "0") int items_per_page,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String category
    )
    {
        if ((type != null && !type.isEmpty()) && (category != null && !category.isEmpty()))
        {
            List<DisciplineDTO> disciplineDTOList = disciplineService.getDisciplineByTypeAndCategory(type, category);
            return new ResponseEntity<>(disciplineDTOList, HttpStatus.OK);
        }else if((type != null && !type.isEmpty()) || (category != null && !category.isEmpty())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (page > 0 && items_per_page > 0) {
            Page<DisciplineDTO> disciplineDTOPage = disciplineService.getAllDisciplines(page, items_per_page);
            return new ResponseEntity<>(disciplineDTOPage, HttpStatus.OK);
        }
        Page<DisciplineDTO> disciplineDTOPage = disciplineService.getAllDisciplines(page, 5);
        return new ResponseEntity<>(disciplineDTOPage, HttpStatus.OK);

    }

    @GetMapping("/{cod}")
    public ResponseEntity<?> getDisciplineById(@PathVariable String cod) {
        return disciplineService.getDisciplineByCod(cod)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
