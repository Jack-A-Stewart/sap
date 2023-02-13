package nl.codegorilla.sap.model;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class UserInput {

    private String email;

    private String courseName;


    public UserInput(String email, String courseName) {
        this.email = email;
        this.courseName = courseName;
    }

    public UserInput() {
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
}
