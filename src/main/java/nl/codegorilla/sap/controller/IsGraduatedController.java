package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.CourseStatusInput;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("/graduated")
public class IsGraduatedController {

    private final CourseStatusService courseStatusService;


    @Autowired
    public IsGraduatedController(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    @PostMapping("/isGraduated")
    public ResponseEntity<?> isGraduated(@RequestBody CourseStatusInput courseStatusInput) {
        return courseStatusService.isGraduated(courseStatusInput);
    }

    @GetMapping("/test")
    public CourseStatusInput testResponse() {
        return new CourseStatusInput("bobcat@meow.com", "Bootcamp");

    }
}
