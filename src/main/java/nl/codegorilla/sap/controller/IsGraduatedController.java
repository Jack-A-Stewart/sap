package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.UserInput;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/graduated")
public class IsGraduatedController {


    private final CourseStatusService courseStatusService;
    private final StudentService studentService;


    @Autowired
    public IsGraduatedController(CourseStatusService courseStatusService, StudentService studentService) {
        this.courseStatusService = courseStatusService;
        this.studentService = studentService;
    }

    @GetMapping("/isGraduated")
    public ResponseEntity<?> isGraduated(@RequestBody UserInput userInput) {

        System.out.println(userInput);
        Optional<Student> student = studentService.findStudentByEmail(userInput.getEmail());

        // to do only return the course status
        return courseStatusService.findCourseStatusById(student.get().getId());

    }

    @GetMapping("/test")
    public UserInput testResponse(){
        return new UserInput("bobcat@meow.com","Bootcamp");

    }





}
