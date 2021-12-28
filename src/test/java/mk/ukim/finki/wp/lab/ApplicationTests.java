package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class ApplicationTests {
    MockMvc mockMvc;

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;

    private static Teacher teacher;
    private static List<Student> students;
    private static boolean dataInit=false;
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
    public void setup(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        dataInit();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getCourses() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/courses");
        mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("coursesList"))
                //.andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent","listCourses"))
        .andExpect(MockMvcResultMatchers.view().name("index"));
    }
    @Test
    void checkHeaders() throws Exception
    {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/login").param("username","admin").param("password","123");
        mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.header().exists("username"));
    }
//    @Test
//    void testDeleteCourse() throws Exception {
//        Course course = courseService.addCourse("WP","test", teacher.getId());
//        course.setStudents(students);
//        MockHttpServletRequestBuilder productDeleteRequest = MockMvcRequestBuilders.delete("/courses/delete/"+course.getCourseId());
//        mockMvc.perform(productDeleteRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/courses"));
//    }

}
