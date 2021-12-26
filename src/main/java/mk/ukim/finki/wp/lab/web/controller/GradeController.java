package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.StudentNotInCourseException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/grades")
public class GradeController {
    private final CourseService courseService;
    private final StudentService studentService;
    private final GradeService gradeService;

    public GradeController(CourseService courseService, StudentService studentService, GradeService gradeService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.gradeService = gradeService;
    }
    @GetMapping("/allGrades")
    public String showGrades(@RequestParam(required = false) String error,
                             @RequestParam(required = false) String from,
                             @RequestParam(required = false) String to,
                             @RequestParam(required = false) String courseId,
                             Model model)
    {
        if(error != null)
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        if(from != null && to != null)
        {
            LocalDateTime fromPar = LocalDateTime.parse(from);
            LocalDateTime toPar = LocalDateTime.parse(to);
            Course c = courseService.findById(Long.parseLong(courseId));
            model.addAttribute("grades",gradeService.findBetween(fromPar,toPar,c));
            model.addAttribute("courses",courseService.listAllCourses());
        }
        else {
            model.addAttribute("grades", gradeService.findAll());
            model.addAttribute("courses", courseService.listAllCourses());
        }
        model.addAttribute("bodyContent","listGrades");
        return "index";
    }
    @GetMapping("/addGrade")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addGrade(@RequestParam(required = false) String studentId,
                           @RequestParam(required = false) String courseId)
    {
        return "add-grade";
    }
    @PostMapping("/saveGrade")
    public String saveGrade(@RequestParam String username,
                            @RequestParam String courseId,
                            @RequestParam String date,
                            @RequestParam String grade)
    {
        Course c = courseService.findById(Long.parseLong(courseId));
        Student s = studentService.findByUsername(username);
        try{
            //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //potrebno za ponatamu, vaka e default
            LocalDateTime dateTime = LocalDateTime.parse(date);
            gradeService.save(new Grade(grade.charAt(0), s, c, dateTime));
        }
        catch(StudentNotInCourseException ex)
        {
            return "redirect:allGrades?error=" + ex.getMessage();
        }
        return "redirect:allGrades";
    }

}
