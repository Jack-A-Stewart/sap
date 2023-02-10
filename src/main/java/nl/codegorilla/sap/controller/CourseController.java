package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        return courseService.deleteCourse(id);
    }

    @PostMapping("/custom")
    public ResponseEntity<?> custom() {
        Course course1 = new Course();
        course1.setName("Bootcamp");
        return courseService.addCourse(course1);
    }


}