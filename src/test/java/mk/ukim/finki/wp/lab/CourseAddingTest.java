//package mk.ukim.finki.wp.lab;
//
//import mk.ukim.finki.wp.lab.model.Course;
//import mk.ukim.finki.wp.lab.model.Teacher;
//import mk.ukim.finki.wp.lab.model.exceptions.CourseAlreadyHereException;
//import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
//import mk.ukim.finki.wp.lab.service.StudentService;
//import mk.ukim.finki.wp.lab.service.TeacherService;
//import mk.ukim.finki.wp.lab.service.impl.CourseServiceImpl;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CourseAddingTest {
//    @Mock
//    private CourseRepository courseRepository;
//    @Mock
//    private StudentService studentService;
//    @Mock
//    private TeacherService teacherService;
//
//    private CourseServiceImpl service;
//
//    @Before
//    public void init()
//    {
//        MockitoAnnotations.openMocks(this);
//        Course course = new Course("Web Programming Test Course","WP Desc");
//        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);
//        service = Mockito.spy(new CourseServiceImpl(courseRepository,studentService,teacherService));
//    }
//    @Test
//    public void testSuccessAdding()
//    {
//        Course add = new Course("Testce","Testcedesc");
//        Course course = service.save(add);
//        Mockito.verify(service).save(add);
//        Assert.assertEquals("Course name do not match","Testce",course.getName());
//        Assert.assertEquals("Course desc do not match","Testcedesc", course.getDescription());
//    }
//    @Test
//    public void testCourseAlreadyHere()
//    {
//        Course course = service.addCourse("Testce","Testcedesc",1L);
//        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course);
//        Assert.assertThrows("CourseAlreadyHere expected",
//                CourseAlreadyHereException.class,
//                () -> service.addCourse("Testce","Testcedesc",1L));
//        Mockito.verify(service).addCourse("Testce","Testcedesc",1L);
//    }
//}
//
