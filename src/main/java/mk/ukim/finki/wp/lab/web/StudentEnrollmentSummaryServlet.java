package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentException;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.parsers.SAXParser;
import java.io.IOException;

@WebServlet(name = "StudentEnrollmentSummaryServlet", value = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummaryServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final CourseService courseService;

    public StudentEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            String username = request.getParameter("student");
            Long course = Long.parseLong((String) request.getSession().getAttribute("courseId"));
            WebContext context = new WebContext(request,response,request.getServletContext());
            context.setVariable("course", courseService.addStudentInCourse(username, course));
            request.getSession().invalidate();
            springTemplateEngine.process("studentsInCourse.html",context,response.getWriter());
        }
        catch(InvalidStudentException z)
        {
            WebContext context = new WebContext(request,response,request.getServletContext());
            context.setVariable("hasError",true);
            context.setVariable("error",z.getMessage());
            context.setVariable("coursesList",courseService.listAllCourses());
            request.getSession().invalidate();
            springTemplateEngine.process("listCourses.html",context,response.getWriter());
        }

    }
}
