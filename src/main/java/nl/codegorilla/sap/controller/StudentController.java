package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
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

    @Autowired
    public StudentController(StudentService studentService, CourseStatusService courseStatusService) {
        this.studentService = studentService;
        this.courseStatusService = courseStatusService;
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
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(studentService.findStudentById(id), HttpStatus.OK);
    }

    /**
     * @param student to be updated
     * @return ResponseEntity with the updated student and status code 200
     */
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
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
    public ResponseEntity<CourseStatus> setCourseStatus(@RequestBody CourseNameStatusDTO courseNameStatus) {
        System.out.println(courseNameStatus);
        return new ResponseEntity<>(courseStatusService.addCourseStatus(courseNameStatus), HttpStatus.CREATED);
    }

    /**
     * @param id to specify which student
     * @return Map of all the course names and status of the specified student
     */
    @GetMapping("/findCourseAndStatus/{id}")
    public ResponseEntity<List<CourseNameStatusDTO>> getAllCourseNameAndCourseStatusByStudentId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseStatusService.courseNameStatusList(id), HttpStatus.OK);
    }
}