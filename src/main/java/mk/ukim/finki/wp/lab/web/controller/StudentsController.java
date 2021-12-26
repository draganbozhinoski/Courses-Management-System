package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;

    public StudentsController(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @GetMapping("/allStudents")
    public String getAllStudents(Model model) {
        model.addAttribute("studentsList", studentService.findAll());
        model.addAttribute("bodyContent","listStudents");
        return "index";
    }

    @GetMapping("/addStudent")
    public String add() {
        return "add-student";
    }

    @PostMapping("/save")
    public String saveStudent(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String username,
                              @RequestParam String password) {
        studentService.save(username, password, name, surname);
        return "redirect:allStudents";
    }

    @PostMapping("/studentEnrollmentSummary")
    public String studentEnrollmentSummary(@RequestParam(required = false) String student,
                                           HttpServletRequest request,
                                           Model model) {
        Long courseId = Long.parseLong((String) request.getSession().getAttribute("courseId"));
        if (student == null) {
            return "redirect:/courses?error=" + "No students were selected!";
        }
        try {
            Course course = courseService.addStudentInCourse(student, courseId);
            List<Grade> gradesByCourse = gradeService.findAllByCourse(course);
            model.addAttribute("course", course);
            model.addAttribute("grades", gradesByCourse);
            model.addAttribute("bodyContent","studentsInCourse");
            return "index";
        } catch (StudentAlreadyInCourseException ex) {
            return "redirect:/courses?error=" + ex.getMessage();
        }
    }

    @GetMapping("/CourseEnrollmentSummary")
    public String CourseEnrollment(Model model) {
        model.addAttribute("studentsList", studentService.findAll());
        model.addAttribute("bodyContent","selectStudent");
        return "index";
    }

    @PostMapping("/courseEnroll")
    public String enroll(@RequestParam String selectedStudent, Model model) {
        String username = selectedStudent.split("-")[2];
        Student toFind = studentService.findByUsername(username);
        List<Course> coursesToShow = courseService.findAllByStudent(toFind);
        model.addAttribute("coursesList", coursesToShow);
        model.addAttribute("student", toFind);
        model.addAttribute("bodyContent","coursesByStudent");
        return "index";
    }

}
