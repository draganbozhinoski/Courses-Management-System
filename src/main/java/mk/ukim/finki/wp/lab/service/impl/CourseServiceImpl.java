package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student z = studentService.findByUsername(username);
        return courseRepository.addStudentToCourse(z,courseRepository.findById(courseId).get());
    }
    @Override
    public Course addCourse(Long courseId, String name) {
        Course vrati = new Course(courseId,name,"Description");
        courseRepository.addCourse(vrati);
        return vrati;
    }

    @Override
    public List<Course> listAllCourses() {
        return courseRepository.findAllCourses();
    }
}
