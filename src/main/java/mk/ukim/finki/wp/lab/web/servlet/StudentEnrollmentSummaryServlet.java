//package mk.ukim.finki.wp.lab.web.servlet;
//
//import mk.ukim.finki.wp.lab.model.Course;
//import mk.ukim.finki.wp.lab.model.Grade;
//import mk.ukim.finki.wp.lab.model.Student;
//import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentException;
//import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
//import mk.ukim.finki.wp.lab.service.CourseService;
//import mk.ukim.finki.wp.lab.service.GradeService;
//import org.thymeleaf.context.WebContext;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet(name = "StudentEnrollmentSummaryServlet", value = "/StudentEnrollmentSummary")
//public class StudentEnrollmentSummaryServlet extends HttpServlet {
//    private final SpringTemplateEngine springTemplateEngine;
//    private final CourseService courseService;
//    private final GradeService gradeService;
//
//    public StudentEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService, GradeService gradeService) {
//        this.springTemplateEngine = springTemplateEngine;
//        this.courseService = courseService;
//        this.gradeService = gradeService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            response.setCharacterEncoding("UTF-8");
//            String username = request.getParameter("student");
//            Long courseId = Long.parseLong((String) request.getSession().getAttribute("courseId"));
//            try {
//                Course course = courseService.addStudentInCourse(username, courseId);
//                List<Grade> gradesByCourse = gradeService.findAllByCourse(course);
//                WebContext context = new WebContext(request,response,request.getServletContext());
//                context.setVariable("course", course);
//                context.setVariable("grades",gradesByCourse);
//                request.getSession().invalidate();
//                springTemplateEngine.process("studentsInCourse.html",context,response.getWriter());
//            }
//            catch(StudentAlreadyInCourseException ex)
//            {
//                request.getSession().setAttribute("error",ex.getMessage());
//                response.sendRedirect("/courses");
//            }
//        }
//        catch(InvalidStudentException z)
//        {
//            WebContext context = new WebContext(request,response,request.getServletContext());
//            context.setVariable("hasError",true);
//            context.setVariable("error",z.getMessage());
//            context.setVariable("coursesList",courseService.listAllCourses());
//            request.getSession().invalidate();
//            springTemplateEngine.process("listCourses.html",context,response.getWriter());
//        }
//
//    }
//}
