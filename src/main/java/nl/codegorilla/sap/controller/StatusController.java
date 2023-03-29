package nl.codegorilla.sap.controller;


import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.model.dto.MailStatusDTO;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin()
@RequestMapping()
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
    @PostMapping("/check")
    public ResponseEntity<MailStatusDTO> isGraduated(@RequestBody CourseStatusInputDTO courseStatusInput) {
        return new ResponseEntity<>(courseStatusService.isGraduated(courseStatusInput), HttpStatus.OK);
    }


    @PostMapping("/upload")
    public ResponseEntity<List<String>> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(fileService.processInput(file));
    }


    @GetMapping("/download/{type}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("type") String type) {
        String path = fileService.processOutput(type);
        return fileService.createResponse(path);
    }

}
