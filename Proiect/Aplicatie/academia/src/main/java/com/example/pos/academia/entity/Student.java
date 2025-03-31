package com.example.pos.academia.entity;

import com.example.pos.academia.dto.StudentDTO;
import com.example.pos.academia.enums.CicluStudii;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String nume;

    @Column(nullable = false)
    private String prenume;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CicluStudii cicluStudii;

    @Column(nullable = false)
    private int anStudiu;

    @Column(nullable = false)
    private int grupa;
//Constructor cu DTO

    public Student(StudentDTO studentDTO) {
        this.id = studentDTO.getId();
        this.nume = studentDTO.getNume();
        this.prenume = studentDTO.getPrenume();
        this.email = studentDTO.getEmail();
        this.cicluStudii = CicluStudii.valueOf(studentDTO.getCicluStudii());
        this.anStudiu = studentDTO.getAnStudiu();
        this.grupa = studentDTO.getGrupa();
    }
}
