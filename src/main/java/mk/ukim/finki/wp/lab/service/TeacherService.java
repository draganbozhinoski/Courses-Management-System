package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> findAll();
    Teacher findById(Long id);
    void addTeacher(String name,String surname,Long id);
    void deleteTeacher(Long id);

}
