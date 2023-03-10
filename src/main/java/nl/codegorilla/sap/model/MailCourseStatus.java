package nl.codegorilla.sap.model;

public class MailCourseStatus {

    private String email;

    private String courseName;
    private String courseStatus;

    public MailCourseStatus(String email, String courseName) {
        this.email = email;
        this.courseName = courseName;
    }


    // courseName is optional so this constructor will be used when it is not given/found
    public MailCourseStatus(String email) {
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
