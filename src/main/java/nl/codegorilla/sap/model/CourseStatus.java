package nl.codegorilla.sap.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CourseStatus {

    @EmbeddedId
    CourseStatusKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("studentId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "course_id")
    Course course;

    private String status;

    public CourseStatus(Student student, Course course, String status) {
        this.id = new CourseStatusKey(student.getId(), course.getId());
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public CourseStatus() {
    }

    public CourseStatusKey getId() {
        return id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(CourseStatusKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}