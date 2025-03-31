package com.example.pos.academia.repository;

import com.example.pos.academia.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    Optional<Student> findById(Long id);

    boolean existsById(Long id);

    List<Student> findByNume(String nume);

    List<Student> findByPrenume(String prenume);

    @Query(value = "SELECT * FROM students WHERE nume LIKE :prefixNume% OR prenume LIKE :prefixNume", nativeQuery = true)
    List<Student> findByPrefixNume(@Param("prefixNume")String prefixNume);

    Boolean existsByNume(String nume);

    void deleteById(int id);
}
