package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentException;
import mk.ukim.finki.wp.lab.repository.InMemory.InMemoryStudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text,text);
    }
    @Override
    public Student findByUsername(String username)
    {
        return studentRepository.findByUsername(username);
    }
    @Override
    public Student save(String username, String password, String name, String surname) {
        Student z = new Student(username,password,name,surname);
        studentRepository.save(z);
        return z;
    }

}
