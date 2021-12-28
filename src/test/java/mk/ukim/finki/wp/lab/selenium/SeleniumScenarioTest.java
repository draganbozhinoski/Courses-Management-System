package mk.ukim.finki.wp.lab.selenium;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;

    private static Teacher teacher;
    private static List<Student> students;
    private static boolean dataInit=false;

    private HtmlUnitDriver htmlUnitDriver;

    void dataInit()
    {
        if(!dataInit)
        {
            teacher = teacherService.addTeacher("Riste","Stojanov");
            studentService.save("user","user","user","user");
            studentService.save("admin","admin","admin","admin");
            students = studentService.findAll();
        }
        dataInit=true;
    }
    @BeforeEach
    void setup()
    {
        htmlUnitDriver = new HtmlUnitDriver(true);
        dataInit();
    }

    @AfterEach
    public void destroy()
    {
        if(htmlUnitDriver != null)
            htmlUnitDriver.close();
    }

    @Test
    public void testScenario() throws Exception
    {
        CoursesPage coursesPage = CoursesPage.to(htmlUnitDriver);
        coursesPage.assertElement(0,0,0,0);
        LoginPage loginPage = LoginPage.login(htmlUnitDriver);
        coursesPage = LoginPage.doLogin(htmlUnitDriver,loginPage,"admin","123");
        coursesPage.assertElement(0,0,0,1);
        coursesPage = AddOrEditCourse.addCourse(htmlUnitDriver,"Web Programiranje","WPDesc");
        coursesPage.assertElement(1,1,1,1);
        coursesPage = AddOrEditCourse.addCourse(htmlUnitDriver,"Web Programiranje2","WPDesc2");
        coursesPage.assertElement(2,2,2,1);

    }
}
