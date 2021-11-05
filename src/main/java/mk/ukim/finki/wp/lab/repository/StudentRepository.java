package mk.ukim.finki.wp.lab.repository;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class StudentRepository {
    public List<Student> students;

    public StudentRepository() {
        this.students = new ArrayList<>(5);
    }
    List<Student> findAllStudents()
    {
        return students;
    }
    List<Student> findAllByNameOrSurname(String text)
    {
        return students.stream().filter(r -> r.getName().contains(text) || r.getSurname().contains(text)).collect(Collectors.toList());
    }
    public void addStudent(Student z)
    {
        students.add(z);
    }

}
