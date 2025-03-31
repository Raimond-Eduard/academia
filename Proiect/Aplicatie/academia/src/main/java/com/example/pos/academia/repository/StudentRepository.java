package com.example.pos.academia.repository;

import com.example.pos.academia.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAll(Pageable pageable);

    Optional<Student> findById(int id);

    Boolean existsById(int id);

    Optional<Student> findByNume(String nume);

    Boolean existsByNume(String nume);

    void deleteById(int id);
}
