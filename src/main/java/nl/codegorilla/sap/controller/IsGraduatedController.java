package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.fileHandling.CsvHandler;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin()
@RequestMapping("/")
public class IsGraduatedController {

    private final CourseStatusService courseStatusService;


    @Autowired
    public IsGraduatedController(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    /**
     * @param courseStatusInput necessary parameters to check the status of a student/course
     * @return True if student has graduated that course and false for anything else
     */
    @PostMapping("/isGraduated")
    public ResponseEntity<?> isGraduated(@RequestBody CourseStatusInputDTO courseStatusInput) {
        return new ResponseEntity<>(courseStatusService.isGraduated(courseStatusInput), HttpStatus.OK);
    }


    @PostMapping("/upload-csv")
    public ResponseEntity<?> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        // Your code to handle the CSV file upload
        CsvHandler csvHandler = new CsvHandler();
        csvHandler.readCsvFile(file);

        return null;
    }

    /**
     * @return Test method
     */
    @GetMapping("/test")
    public CourseStatusInputDTO testResponse() {
        return new CourseStatusInputDTO("bobcat@meow.com", "Bootcamp");
    }


}
