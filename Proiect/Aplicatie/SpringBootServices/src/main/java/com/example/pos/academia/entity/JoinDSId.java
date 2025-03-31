package com.example.pos.academia.entity;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JoinDSId implements Serializable {
    private Long studentId;
    private String disciplineCod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinDSId that = (JoinDSId) o;
        return studentId.equals(that.studentId) && disciplineCod.equals(that.disciplineCod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, disciplineCod);
    }
}
