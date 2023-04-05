package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    /**
     * @return list of all courses
     */
    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.findAllCourses();
    }


    /**
     * @param id to identify the course by
     * @return ResponseEntity with the found course and status code 200
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<Course> findCourseById(@PathVariable("id") Long id) {
        Course course = courseService.findCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    /**
     * @param course to be added
     * @return ResponseEntity with the added course and status code 201
     */
    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    /**
     * @param id of the course to be deleted
     * @return Response entity with status code 200
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param course to be updated
     * @return ResponseEntity with the updated course and status code 200
     */
    @PutMapping("/update")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updateCourse = courseService.updateCourse(course);
        return new ResponseEntity<>(updateCourse, HttpStatus.OK);
    }

}
