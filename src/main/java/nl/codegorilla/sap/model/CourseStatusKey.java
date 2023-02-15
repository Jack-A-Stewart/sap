package nl.codegorilla.sap.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourseStatusKey implements Serializable {


    @Column(name = "student_id")
    long studentId;

    @Column(name = "course_id")
    long courseId;

    public CourseStatusKey(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public CourseStatusKey() {
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseStatusKey that)) return false;
        return getStudentId() == that.getStudentId() && getCourseId() == that.getCourseId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getCourseId());
    }
}