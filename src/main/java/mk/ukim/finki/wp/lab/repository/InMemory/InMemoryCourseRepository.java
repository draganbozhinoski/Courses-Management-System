package mk.ukim.finki.wp.lab.repository.InMemory;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Data
public class InMemoryCourseRepository {
    List<Course> courses;

    public InMemoryCourseRepository() {
        this.courses = new ArrayList<>(5);
//        Course a = new Course("Веб Програмирање","Web Programming");
//        Course b = new Course("Оперативни Системи","OS");
//        Course c = new Course("Електронска и мобилна трговија","EIMT");
//        Course d = new Course("Компјутерски мрежи","KM");
    }
    public List<Course> findAllCourses(){
        return courses;
    }
    public Optional<Course> findById(Long courseId){
        return courses.stream().filter(r -> r.getCourseId().equals(courseId)).findFirst();
    }
    public List<Student> findAllStudentsByCourse(Long courseId)
    {
        return findById(courseId).get().getStudents();
    }
    public Course addStudentToCourse(Student student, Course course)
    {
        course.getStudents().add(student);
        return course;
    }
    public Course addCourse(Course course)
    {
        courses.add(course);
        return course;
    }
    public void deleteCourse(Long id)
    {
        courses.removeIf(c -> c.getCourseId().equals(id));
    }
    public Optional<Course> findByName(String name)
    {
        return courses.stream().filter(c -> c.getName().equals(name)).findFirst();
    }
}
