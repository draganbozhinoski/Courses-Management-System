package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentException;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
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
    public List<Student> listAll() {
        return studentRepository.students;
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.students.stream().filter(r -> r.getName().equals(text) || r.getSurname().equals(text)).collect(Collectors.toList());
    }
    @Override
    public Student findByUsername(String username)
    {
        if(studentRepository.students.stream().filter(r -> r.getUsername().equals(username)).count() <= 0)
        {
            throw new InvalidStudentException();
        }
        return studentRepository.students.stream().filter(r -> r.getUsername().equals(username)).collect(Collectors.toList()).get(0);
    }
    @Override
    public Student save(String username, String password, String name, String surname) {
        Student z = new Student(username,password,name,surname);
        studentRepository.addStudent(z);
        return z;
    }
}
