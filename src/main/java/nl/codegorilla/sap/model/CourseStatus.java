package nl.codegorilla.sap.model;

import jakarta.persistence.OneToOne;

public class CourseStatus {

    @OneToOne(targetEntity = Student.class, mappedBy = "id")
    private final long studentId;
    @OneToOne(targetEntity = Course.class, mappedBy = "id")
    private final long courseId;
    private final int courseStatus;


    public CourseStatus(long studentId, long courseId, int courseStatus) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseStatus = courseStatus;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public int getCourseStatus() {
        return courseStatus;
    }
}
