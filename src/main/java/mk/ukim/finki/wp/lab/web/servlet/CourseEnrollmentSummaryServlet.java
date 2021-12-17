package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CourseEnrollmentSummaryServlet", value = "/CourseEnrollmentSummary")
public class CourseEnrollmentSummaryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final StudentService studentService;
    private final CourseService courseService;
    public CourseEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, StudentService studentService, CourseService courseService) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentService.findAll();
        WebContext context = new WebContext(request,response, request.getServletContext());
        context.setVariable("studentsList",students);
        springTemplateEngine.process("selectStudent.html",context,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("selectedStudent").split("-")[2];
        Student toFind = studentService.findByUsername(username);
        response.setCharacterEncoding("UTF-8");
        List<Course> coursesToShow = new ArrayList<>();
        for(Course c : courseService.listAllCourses())
        {
            List<Student> studentsInCourse = c.getStudents();
            if(studentsInCourse.contains(toFind))
                coursesToShow.add(c);
        }
        WebContext context = new WebContext(request,response, request.getServletContext());
        context.setVariable("coursesList",coursesToShow);
        context.setVariable("student",toFind);
        springTemplateEngine.process("coursesByStudent.html",context,response.getWriter());
    }
}
