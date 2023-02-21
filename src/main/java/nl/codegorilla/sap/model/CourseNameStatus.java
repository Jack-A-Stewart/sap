package nl.codegorilla.sap.model;

import java.io.Serializable;

public class CourseNameStatus implements Serializable {

    private Long id;
    private String courseName;
    private String status;


    public CourseNameStatus() {
    }

    public CourseNameStatus(Long id, String courseName, String status) {
        this.id = id;
        this.courseName = courseName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SetStatus{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
