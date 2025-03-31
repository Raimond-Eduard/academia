package com.example.pos.academia.repository;

import com.example.pos.academia.entity.CadreDidactice;
import com.example.pos.academia.enums.GradDidactic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CadreDidacticeRepository extends JpaRepository<CadreDidactice, Long> {
    Page<CadreDidactice> findAll(Pageable pageable);

    Optional<CadreDidactice> findById(int id);

    Boolean existsById(int id);

    List<CadreDidactice> findByNume(String nume);

    List<CadreDidactice> findByPrenume(String prenume);

    List<CadreDidactice> findByGradDidactic(@Param("acad_rank")GradDidactic gradDidactic);

    @Query(value = "SELECT * FROM cadre_didactice WHERE nume LIKE :prefixNume% OR prenume LIKE :prefixNume%", nativeQuery = true)
    List<CadreDidactice> findByPrefixNume(@Param("prefixNume")String prefixNume);



    Boolean existsByNume(String nume);

    void deleteById(int id);

}
