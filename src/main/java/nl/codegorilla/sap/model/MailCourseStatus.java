package nl.codegorilla.sap.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Embeddable;

@Embeddable
public class MailCourseStatus {

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String course;

    @CsvBindByName
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MailCourseStatus{" +
                "email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
