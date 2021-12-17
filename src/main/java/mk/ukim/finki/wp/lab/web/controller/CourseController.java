package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.CourseAlreadyHereException;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CoursePagingRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GradeService gradeService;
    private final CoursePagingRepository coursePagingRepository;

    public CourseController(CourseService courseService, TeacherService teacherService, GradeService gradeService, CoursePagingRepository coursePagingRepository) {
        this.courseService = courseService;
        this.teacherService = teacherService;

        this.gradeService = gradeService;
        this.coursePagingRepository = coursePagingRepository;
    }
    @GetMapping()
    public String getCoursesPage(@RequestParam(required = false) String error, HttpServletRequest req, Model model) {
        if(error != null || req.getSession().getAttribute("error") != null)
        {
            if(req.getSession().getAttribute("error") != null)
                error = (String) req.getSession().getAttribute("error");
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
    public String addStudentPage(@RequestParam(required = false) String courseId,HttpServletRequest request) {
        request.getSession().setAttribute("courseId",courseId);
        return "redirect:/AddStudent";
    }
    @GetMapping("/addCourse")
    public String addCourse(Model model) {
        model.addAttribute("listTeachers",teacherService.findAll());
        return "add-course";
    }
    @PostMapping("/add")
    public String saveCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam String teacherId,
                             @RequestParam(required = false) String courseId) {
        if(courseId != null)
        {
            try {
                Course zemi = courseService.findById(Long.parseLong(courseId));
                if(courseService.findByName(name) != null)
                    return "redirect:/courses?error=" + "Ne mozete da promenite ime na kurs so vekje postoecko od listata!";
                zemi.setName(name);
                zemi.setDescription(description);
                zemi.setTeacher(teacherService.findById(Long.parseLong(teacherId)));
                courseService.save(zemi);
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
    public String editCourse(@PathVariable String id,Model model) {
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
    @Transactional
    public String deleteCourse(@PathVariable String id) {
        Course najdi = courseService.findById(Long.parseLong(id));
        gradeService.deleteGradeByCourse(najdi);
        courseService.deleteCourse(Long.parseLong(id));
        return "redirect:/courses";
    }
    @GetMapping("prev")
    public String getPrev(HttpServletRequest request,Model model)
    {
        Integer momentalna = (Integer) request.getSession().getAttribute("momentalnaStrana");
        if(momentalna == null)
        {
            momentalna = 0;
            request.getSession().setAttribute("momentalnaStrana",0);
        }
        else
        {
            if(momentalna>=1) {
                momentalna -= 2;
                request.getSession().setAttribute("momentalnaStrana", momentalna);
            }
        }
        return "redirect:paging";
    }
    @GetMapping("/paging")
    public String showPaging(Model model,HttpServletRequest request)
    {
        Integer momentalna = (Integer) request.getSession().getAttribute("momentalnaStrana");
        if(momentalna == null)
        {
            momentalna = 0;
            request.getSession().setAttribute("momentalnaStrana",0);
        }
        else
        {
            momentalna++;
            request.getSession().setAttribute("momentalnaStrana",momentalna);
        }
        Pageable triElementi = PageRequest.of(momentalna,3);
        Page<Course> allCourses = coursePagingRepository.findAll(triElementi);
        model.addAttribute("coursesList",allCourses);
        model.addAttribute("paging",true);
        if(allCourses.getSize() < 1) {
            request.getSession().setAttribute("momentalnaStrana",0);
            return "redirect:courses/paging";
        }
        return "listCourses";
    }

//    @GetMapping("/populate")
//    public String populate() {
//        try {
//            courseService.addCourse("Web Programming", "Spring", 0L);
//            courseService.addCourse("Probability and Statistics", "Maths", 1L);
//            courseService.addCourse("Databases", "SQL", 2L);
//        }
//        catch(CourseAlreadyHereException ex)
//        {
//            return "redirect:/courses?error=" + ex.getMessage();
//        }
//        return "redirect:/courses";
//    }
}
