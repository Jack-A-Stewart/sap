package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.service.CourseService;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> getAllStudents() {
        return studentService.findAllStudents();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        return studentService.findStudentById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        return studentService.deleteStudent(id);
    }


    // test method that creates 2 CourseStatus's with different students and courses.
    @PostMapping("/custom")
    public ResponseEntity<?> custom() {
        Student student = new Student();
        student.setFirstName("Bob");
        student.setLastName("Cat");
        student.setEmail("bobcat@meow.com");
        studentService.addStudent(student);
        Course course = new Course();
        course.setName("Bootcamp");
        courseService.addCourse(course);
        CourseStatus courseStatus = new CourseStatus(student, course, "Completed");
        courseStatusService.addCourseStatus(courseStatus);

        Student student2 = new Student();
        student2.setFirstName("Jack");
        student2.setLastName("Stewart");
        student2.setEmail("jackthemango@gmail.com");
        studentService.addStudent(student2);
        Course course2 = new Course();
        course2.setName("DevOps");
        courseService.addCourse(course2);
        CourseStatus courseStatus2 = new CourseStatus(student2, course2, "Completed");
        return courseStatusService.addCourseStatus(courseStatus2);
    }
}