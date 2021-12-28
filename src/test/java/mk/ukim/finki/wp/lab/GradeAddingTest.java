package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.StudentNotInCourseException;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.impl.GradeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class GradeAddingTest {
    @Mock
    private GradeRepository gradeRepository;

    private GradeServiceImpl service;

    @Before
    public void init()
    {
        MockitoAnnotations.openMocks(this);
        this.service = Mockito.spy(new GradeServiceImpl(gradeRepository));
        Grade grade = new Grade('A',
                new Student("dboz","pw","dragan","bozh"),
                new Course("WP","wpDesc"),
                LocalDateTime.now());
        Mockito.when(this.gradeRepository.save(Mockito.any(Grade.class))).thenReturn(grade);

    }
    @Test
    public void testAddGradeException()
    {
        Grade grade = new Grade('A',
                new Student("dboz","pw","dragan","bozh"),
                new Course("WP","wpDesc"),
                LocalDateTime.now());
        Assert.assertThrows("Expected student not in course",
                StudentNotInCourseException.class,
                () -> service.save(grade));
        Mockito.when(gradeRepository.save(Mockito.any(Grade.class))).thenReturn(grade);
        Mockito.verify(service).save(grade);
    }

}
