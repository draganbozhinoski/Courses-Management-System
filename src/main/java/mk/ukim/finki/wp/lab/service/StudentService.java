package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    List<Student> findAllByNameOrSurname(String text);

    Student findByUsername(String username);

    Student save(String username, String password, String name, String surname);
}