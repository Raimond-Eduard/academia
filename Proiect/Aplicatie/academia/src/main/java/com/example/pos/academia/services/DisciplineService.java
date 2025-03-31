package com.example.pos.academia.services;


import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.enums.CategorieDisciplina;
import com.example.pos.academia.enums.TipDisciplina;
import com.example.pos.academia.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;

    public List<DisciplineDTO> getAllDisciplines(){
        return disciplineRepository.findAll().stream()
                .map(DisciplineDTO::new)
                .collect(Collectors.toList());
    }

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
}
