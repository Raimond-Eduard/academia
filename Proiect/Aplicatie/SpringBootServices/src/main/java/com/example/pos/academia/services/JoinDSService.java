package com.example.pos.academia.services;

import com.example.pos.academia.dto.DisciplineDTO;
import com.example.pos.academia.entity.Discipline;
import com.example.pos.academia.entity.JoinDSEntity;
import com.example.pos.academia.entity.JoinDSId;
import com.example.pos.academia.entity.Student;
import com.example.pos.academia.repository.DisciplineRepository;
import com.example.pos.academia.repository.JoinDSRepository;
import com.example.pos.academia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JoinDSService {

    @Autowired
    private JoinDSRepository joinDSRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    public void assign(Long studentId, String disciplineCod) {
        Student student = studentRepository.findById(studentId).isPresent() ? studentRepository.findById(studentId).get() : null;
        Discipline discipline = disciplineRepository.findByCod(disciplineCod).isPresent() ? disciplineRepository.findByCod(disciplineCod).get() : null;

        if(student == null || discipline == null) {
            throw new RuntimeException("Student or Discipline not found");
        }

        JoinDSEntity joinDS = new JoinDSEntity();
        joinDS.setId(new JoinDSId(studentId, disciplineCod));
        joinDS.setDiscipline(discipline);
        joinDS.setStudent(student);

        joinDSRepository.save(joinDS);
    }
    public List<Integer> getStudentIds(String cod) {
        return joinDSRepository.getStudentsByDisciplinaCod(cod);
    }

    public List<String> getDisciplineCodes(Long studentId) {
        List<String> list = joinDSRepository.getDisciplineByIdStudent(studentId);
        for (int i = 0; i < list.size(); i++) {
            int index = list.get(i).indexOf(',');
            if(index != -1) {
                list.set(i, list.get(i).substring(index + 1));
            }
        }
        return list;
    }

    public List<String> getCourseCodesByStudentId(Long studentId) {
        return joinDSRepository.findByStudentId(studentId)
                .stream()
                .map(join -> join.getDiscipline().getCod())
                .collect(Collectors.toList());

    }

}
