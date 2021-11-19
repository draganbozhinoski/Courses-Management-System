package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.CourseAlreadyHereException;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;

    }
    @GetMapping()
    public String getCoursesPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model)
    {
        if(error != null)
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        ServletContext context = req.getServletContext();
        int users =(int) context.getAttribute("users");
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60);
        model.addAttribute("coursesList",courseService
                .listAllCourses()
                .stream()
                .sorted(Comparator.comparing(Course::getName))
                .collect(Collectors.toList()));
        model.addAttribute("users",users);
        return "listCourses";
    }
    @PostMapping()
    public String addStudentPage(@RequestParam(required = false) String courseId,HttpServletRequest request)
    {
        request.getSession().setAttribute("courseId",courseId);
        return "redirect:/AddStudent";
    }
    @GetMapping("/addCourse")
    public String addCourse(Model model)
    {
        model.addAttribute("listTeachers",teacherService.findAll());
        return "add-course";
    }
    @PostMapping("/add")
    public String saveCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String teacherId,
                             @RequestParam(required = false) String courseId)
    {
        if(courseId != null)
        {
            try {
                Course zemi = courseService.findById(Long.parseLong(courseId));
                if(courseService.findByName(name) != null)
                    return "redirect:/courses?error=" + "Ne mozete da promenite ime na kurs so vekje postoecko od listata!";
                zemi.setName(name);
                zemi.setDescription(description);
                zemi.setTeacher(teacherService.findById(Long.parseLong(teacherId)));
            }
            catch(CourseNotFoundException ex)
            {
                return "redirect:/courses?error=" + ex.getMessage();
            }
        }
        else {
            try {
                courseService.addCourse(name, description, Long.parseLong(teacherId));
            }
            catch(CourseAlreadyHereException ex) {
                return "redirect:/courses?error=" + ex.getMessage();
            }
        }
        return "redirect:/courses";
    }
    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable String id,Model model)
    {
        try {
            Course toEdit = courseService.findById(Long.parseLong(id));
            List<Teacher> listTeachers = teacherService.findAll();
            model.addAttribute("course", toEdit);
            model.addAttribute("listTeachers",listTeachers);
        }
        catch(CourseNotFoundException ex)
        {
            return "redirect:/courses?error=" + ex.getMessage();
        }
        return "add-course";
    }
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable String id)
    {
        courseService.deleteCourse(Long.parseLong(id));
        return "redirect:/courses";
    }
    @GetMapping("/populate")
    public String populate()
    {
        try {
            courseService.addCourse("Web Programming", "Spring", 0L);
            courseService.addCourse("Probability and Statistics", "Maths", 1L);
            courseService.addCourse("Databases", "SQL", 2L);
        }
        catch(CourseAlreadyHereException ex)
        {
            return "redirect:/courses?error=" + ex.getMessage();
        }
        return "redirect:/courses";
    }
}
