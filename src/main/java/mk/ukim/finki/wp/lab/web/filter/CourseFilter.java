package mk.ukim.finki.wp.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(filterName = "CourseFilter")
public class CourseFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        String ID = (String) req.getSession().getAttribute("courseId");
        String pattern = "/courses/edit/-?\\d+";
        boolean matches = Pattern.matches(pattern,path);
        if(!path.equals("/courses") && ID==null
                && !path.equals("/CourseEnrollmentSummary")
                && !path.equals("/Logout")
                && !path.equals("/courses/addCourse")
                && !path.equals("/courses/add")
                && !path.equals("/courses/populate")
                && !matches
                && !Pattern.matches("/courses/delete/\\d+",path))
            resp.sendRedirect("/courses");
        else
            chain.doFilter(request, response);
    }
}
