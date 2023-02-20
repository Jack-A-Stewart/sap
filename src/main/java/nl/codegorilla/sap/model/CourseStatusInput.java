package nl.codegorilla.sap.model;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class CourseStatusInput {

    private String email;

    private String courseName;


    public CourseStatusInput(String email, String courseName) {
        this.email = email;
        this.courseName = courseName;
    }

    public CourseStatusInput() {
    }

    public String getEmail() {
        return email;
    }


    public String getCourseName() {
        return courseName;
    }

}
