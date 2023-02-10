package nl.codegorilla.sap.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, name = "id")
    private long id;

    private String name;

    @OneToMany
    private Set<CourseStatus> courseStatusSet;


    public Course() {
    }

    public Course(long id, String name, Set<CourseStatus> courseStatusSet) {
        this.id = id;
        this.name = name;
        this.courseStatusSet = courseStatusSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseStatus> getCourseStatusSet() {
        return courseStatusSet;
    }

    public void setCourseStatusSet(Set<CourseStatus> courseStatusSet) {
        this.courseStatusSet = courseStatusSet;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" +
                '}';
    }
}
