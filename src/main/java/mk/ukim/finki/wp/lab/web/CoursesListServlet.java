package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CoursesListServlet", value = "/listCourses")
public class CoursesListServlet extends HttpServlet {
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public CoursesListServlet(CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        WebContext context = new WebContext(request,response,request.getServletContext());
        context.setVariable("coursesList",courseService.listAllCourses());
        springTemplateEngine.process("listCourses.html",context,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedCourse = request.getParameter("courseId");
        if(selectedCourse != null) {
//            String courseName = selectedCourse.split("-")[1];
            String id = selectedCourse.split("-")[0];
//            courseService.addCourse(Long.parseLong(id), courseName);
            request.getSession().setAttribute("courseId", id);
        }
        response.sendRedirect("/AddStudent");
    }
}
