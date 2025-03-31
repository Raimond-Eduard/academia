package com.example.pos.academia.repository;

import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.entity.JoinDSEntity;
import com.example.pos.academia.entity.JoinDSId;
import com.example.pos.academia.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JoinDSRepository extends JpaRepository<JoinDSEntity, JoinDSId> {

    @Query(value = "SELECT * FROM join_DS WHERE DisciplinaID = :disciplinaId", nativeQuery = true)
    List<Integer> getStudentsByDisciplinaCod(@Param("disciplinaId")String cod);

    @Query(value = "SELECT * FROM join_DS WHERE StudentId = :studentId", nativeQuery = true)
    List<String> getDisciplineByIdStudent(@Param("studentId")Long studentId);

    List<JoinDSEntity> findByStudentId(Long studentId);

}
