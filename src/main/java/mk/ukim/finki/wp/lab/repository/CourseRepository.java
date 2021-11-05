package mk.ukim.finki.wp.lab.repository;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Data
public class CourseRepository {
    List<Course> courses;

    public CourseRepository() {
        this.courses = new ArrayList<>(5);
        courses.add(new Course(1L,"Веб Програмирање","Web Programming"));
        courses.add(new Course(2L,"Оперативни Системи","OS"));
        courses.add(new Course(3L,"Електронска и мобилна трговија","EIMT"));
        courses.add(new Course(4L,"Компјутерски мрежи","KM"));
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
}
