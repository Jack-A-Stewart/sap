package nl.codegorilla.sap.model;

import com.opencsv.bean.CsvBindByName;

public class TempFileObject {

    @CsvBindByName(column = "email")
    private String email;
    @CsvBindByName(column = "course")
    private String courseName;
    private String courseStatus;

    public TempFileObject(String email, String courseName, String courseStatus) {
        this.email = email;
        this.courseName = courseName;
        this.courseStatus = courseStatus;
    }


    // courseName is optional so this constructor will be used when it is not given/found
    public TempFileObject(String email, String courseStatus) {
        this.email = email;
        this.courseStatus = courseStatus;
    }

    public TempFileObject(String email) {
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
