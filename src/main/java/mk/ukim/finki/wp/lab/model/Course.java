package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;

    String name;

    String description;

    @Enumerated(value = EnumType.STRING)
    private Type type;
    @ManyToMany(fetch = FetchType.EAGER)
    List<Student> students;
    @ManyToOne
    Teacher teacher;
    public Course(String name, String description) {
        Random random = new Random();
        courseId = random.nextLong();
        this.name = name;
        this.description = description;
        students = new ArrayList<>();
        type = Type.WINTER;
    }

    public Course() {

    }
}
