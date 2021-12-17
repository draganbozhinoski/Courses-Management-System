package mk.ukim.finki.wp.lab.repository.InMemory;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Data
public class InMemoryStudentRepository {
    public List<Student> students;

    public InMemoryStudentRepository() {
        this.students = new ArrayList<>(5);
        students.add(new Student("testeruser1","test","Dragan Bozhinoski 1","tester"));
        students.add(new Student("testeruser2","test","Jovan Jovanov 2","tester"));
        students.add(new Student("testeruser3","test","Stojan Stojanov 3","tester"));

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
