package com.example.pos.academia.services;

import com.example.pos.academia.dto.StudentDTO;
import com.example.pos.academia.enums.CicluStudii;
import com.example.pos.academia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id).map(StudentDTO::new);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO studentDTO) {
        return studentRepository.findById(id).map(existingStudent ->
        {
            existingStudent.setNume(studentDTO.getNume());
            existingStudent.setPrenume(studentDTO.getPrenume());
            existingStudent.setEmail(studentDTO.getEmail());
            existingStudent.setGrupa(studentDTO.getGrupa());
            existingStudent.setAnStudiu(studentDTO.getAnStudiu());
            existingStudent.setCicluStudii(CicluStudii.valueOf(studentDTO.getCicluStudii()));
            return new StudentDTO(existingStudent);
        });
    }



}
