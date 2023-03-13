package nl.codegorilla.sap.controller;

import com.opencsv.exceptions.CsvException;
import nl.codegorilla.sap.fileHandling.CsvHandler;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/check")
public class IsGraduatedController {

    private final CourseStatusService courseStatusService;
    private final CsvHandler csvHandler;


    @Autowired
    public IsGraduatedController(CourseStatusService courseStatusService, CsvHandler csvHandler) {
        this.courseStatusService = courseStatusService;
        this.csvHandler = csvHandler;
    }

    /**
     * @param courseStatusInput necessary parameters to check the status of a student/course
     * @return True if student has graduated that course and false for anything else
     */
    @PostMapping("/single")
    public ResponseEntity<?> isGraduated(@RequestBody CourseStatusInputDTO courseStatusInput) {
        return new ResponseEntity<>(courseStatusService.isGraduated(courseStatusInput), HttpStatus.OK);
    }


    @PostMapping("/csv")
    public ResponseEntity<?> uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException, CsvException {
        // Your code to handle the CSV file upload
        List<String[]> strings = csvHandler.readCsvFile(file);
        return csvHandler.write(strings, file);
    }


}
