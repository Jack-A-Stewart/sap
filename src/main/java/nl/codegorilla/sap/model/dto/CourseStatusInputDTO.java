package nl.codegorilla.sap.model.dto;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class CourseStatusInputDTO {

    private String email;

    private String courseName;


    public CourseStatusInputDTO(String email, String courseName) {
        this.email = email;
        this.courseName = courseName;
    }

    public CourseStatusInputDTO() {
    }

    public String getEmail() {
        return email;
    }


    public String getCourseName() {
        return courseName;
    }

}
