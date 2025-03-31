package com.example.pos.academia.services;

import com.example.pos.academia.dto.CadreDidacticeDTO;
import com.example.pos.academia.enums.GradDidactic;
import com.example.pos.academia.enums.TipAsociere;
import com.example.pos.academia.repository.CadreDidacticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

}
