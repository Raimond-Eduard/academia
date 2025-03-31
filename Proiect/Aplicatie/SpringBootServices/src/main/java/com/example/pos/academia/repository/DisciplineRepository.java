package com.example.pos.academia.repository;

import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.enums.CategorieDisciplina;
import com.example.pos.academia.enums.TipDisciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<Discipline, String> {

    Page<Discipline> findAll(Pageable pageable);

    Optional<Discipline> findByCod(String cod);

    boolean existsByCod(String cod);

    @Query(value = "SELECT * FROM discipline WHERE tip_disciplina = :type AND categorie_disciplina = :category", nativeQuery = true)
    List<Discipline> findByTypeAndCategory(@Param("type")String type, @Param("category")String category);

    void deleteByCod(String cod);

    List<Discipline> findByTitularId(Long idTitular);
}
