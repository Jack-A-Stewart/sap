package nl.codegorilla.sap.model.dto;

public class CourseStatusInputDTO {

    private final String email;

    private final String courseName;


    public CourseStatusInputDTO(String email, String courseName) {
        this.email = email;
        this.courseName = courseName;
    }
    

    public String getEmail() {
        return email;
    }


    public String getCourseName() {
        return courseName;
    }

}
