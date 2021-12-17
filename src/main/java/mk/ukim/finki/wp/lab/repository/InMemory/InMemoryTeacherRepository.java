package mk.ukim.finki.wp.lab.repository.InMemory;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.TeacherFullName;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryTeacherRepository {
    List<Teacher> teacherList;

    public InMemoryTeacherRepository() {
//        this.teacherList = new ArrayList<>(5);
//        teacherList.add(new Teacher("Riste","Stojanov"));
//        teacherList.add(new Teacher("Sasho","Gramatikov"));
//        teacherList.add(new Teacher("Dragan","Bozhinoski"));
//        teacherList.add(new Teacher("Kostadin","Mishev"));
//        teacherList.add(new Teacher("Ana","Todorovska"));
    }
    public List<Teacher> findAll()
    {
        return teacherList;
    }
    public Teacher findById(Long id)
    {
        return teacherList.stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList()).get(0);
    }
    public void addTeacher(String name,String surname,Long id)
    {
        teacherList.add(new Teacher(new TeacherFullName(name,surname)));
    }
    public void deleteTeacher(Long id)
    {
        teacherList.removeIf(t -> t.getId().equals(id));
    }
}
