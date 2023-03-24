package nl.codegorilla.sap.controller;


import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin()
@RequestMapping("/check")
public class StatusController {

    private final CourseStatusService courseStatusService;

    private final FileService fileService;


    @Autowired
    public StatusController(CourseStatusService courseStatusService, FileService fileService) {
        this.courseStatusService = courseStatusService;
        this.fileService = fileService;
    }


    /**
     * @param courseStatusInput necessary parameters to check the status of a student/course
     * @return True if student has graduated that course and false for anything else
     */
    @PostMapping("/single")
    public ResponseEntity<?> isGraduated(@RequestBody CourseStatusInputDTO courseStatusInput) {
        return new ResponseEntity<>(courseStatusService.isGraduated(courseStatusInput), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String path = fileService.process(file);
        return fileService.createResponse(path, file);
    }
}
