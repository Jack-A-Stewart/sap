package nl.codegorilla.sap.model;

import jakarta.persistence.*;

@Entity
public class CourseStatus {

    @EmbeddedId
    CourseStatusKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    String status;

    public CourseStatus(CourseStatusKey id, Student student, Course course, String status) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public CourseStatus() {
    }

    public CourseStatusKey getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}