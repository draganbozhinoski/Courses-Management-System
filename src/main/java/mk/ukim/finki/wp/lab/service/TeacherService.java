package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    Teacher findById(Long id);
    Teacher addTeacher(String name, String surname);
    void deleteTeacher(Long id);
    void save(Teacher t);

}
