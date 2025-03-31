package com.example.pos.academia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="join_DS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JoinDSEntity {

    @EmbeddedId
    private JoinDSId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "StudentID")
    private Student student;

    @ManyToOne
    @MapsId("disciplineCod")
    @JoinColumn(name = "DisciplinaID")
    private Discipline discipline;

}
