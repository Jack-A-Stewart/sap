package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
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


    /**
     * @return ResponseEntity with List of all students and status code 200
     */
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * @param student to be added
     * @return ResponseEntity with the added student and status code 201
     */
    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    /**
     * @param id id to search the student by
     * @return ResponseEntity with the found Student and a status code 200
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    /**
     * @param student to be updated
     * @return ResponseEntity with the updated student and status code 200
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    /**
     * @param id of the student to be deleted
     * @return Response entity with status code 200
     */

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * @param courseNameStatus details for the to be added CourseStatus
     * @return ResponseEntity with the added CourseStatus and status code 201
     */
    @PostMapping("/setCourseStatus")
    public ResponseEntity<?> setCourseStatus(@RequestBody CourseNameStatusDTO courseNameStatus) {
        System.out.println(courseNameStatus);
        return new ResponseEntity<>(courseStatusService.addCourseStatus(courseNameStatus), HttpStatus.CREATED);
    }


    /**
     * @param id to specify which student
     * @return Map of all the course names and status of the specified student
     */
    @GetMapping("/findCourseAndStatus/{id}")
    public ResponseEntity<?> getAllCourseNameAndCourseStatusByStudentId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseStatusService.courseNameStatusList(id), HttpStatus.OK);
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
        CourseStatus courseStatus = new CourseStatus(student1, course, "graduated");
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

        CourseStatus courseStatus3 = new CourseStatus(student1, devOps, "graduated");
        courseStatusService.addCourseStatus(courseStatus3);

        return new ResponseEntity<>(courseStatusService.addCourseStatus(courseStatus2), HttpStatus.OK);
    }
}