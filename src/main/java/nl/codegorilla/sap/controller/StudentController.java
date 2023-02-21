package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseNameStatus;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.service.CourseService;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final CourseStatusService courseStatusService;
    private final CourseService courseService;

    @Autowired
    public StudentController(StudentService studentService, CourseStatusService courseStatusService, CourseService courseService) {
        this.studentService = studentService;
        this.courseStatusService = courseStatusService;
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/setCourseStatus")
    public ResponseEntity<?> setCourseStatus(@RequestBody CourseNameStatus courseNameStatus) {
        return new ResponseEntity<>(courseStatusService.addCourseStatus(courseNameStatus), HttpStatus.CREATED);
    }

    // test method that creates 2 CourseStatus's with different students and courses.
    @PostMapping("/custom")
    public ResponseEntity<?> custom() {
        Student student1 = new Student();
        student1.setFirstName("Bob");
        student1.setLastName("Cat");
        student1.setEmail("bobcat@meow.com");
        studentService.addStudent(student1);
        Course course = new Course();
        course.setName("Bootcamp");
        courseService.addCourse(course);
        CourseStatus courseStatus = new CourseStatus(student1, course, "Completed");
        courseStatusService.addCourseStatus(courseStatus);

        Student student2 = new Student();
        student2.setFirstName("Jack");
        student2.setLastName("Stewart");
        student2.setEmail("jackstewart@gmail.com");
        studentService.addStudent(student2);
        Course devOps = new Course();
        devOps.setName("DevOps");
        courseService.addCourse(devOps);
        CourseStatus courseStatus2 = new CourseStatus(student2, devOps, "Not done");

        CourseStatus courseStatus3 = new CourseStatus(student1, devOps, "Completed");
        courseStatusService.addCourseStatus(courseStatus3);

        return courseStatusService.addCourseStatus(courseStatus2);


    }
}