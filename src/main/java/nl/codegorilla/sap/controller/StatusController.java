package nl.codegorilla.sap.controller;


import jakarta.servlet.http.HttpSession;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.model.dto.MailStatusDTO;
import nl.codegorilla.sap.service.CourseStatusService;
import nl.codegorilla.sap.service.FileService;
import nl.codegorilla.sap.service.JwtService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping()
public class StatusController {

    public String userEmail;
    private final CourseStatusService courseStatusService;

    private final FileService fileService;

    private final JwtService jwtService;


    //    @Autowired
    public StatusController(CourseStatusService courseStatusService, FileService fileService, JwtService jwtService) {
        this.courseStatusService = courseStatusService;
        this.fileService = fileService;
        this.jwtService = jwtService;
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
    public ResponseEntity<List<String>> upload(@RequestParam("file") MultipartFile file, HttpSession session, @RequestHeader(name = "Authorization") String token) {

        String jwt;
//        System.out.println(token);
        jwt = token.substring(7);
//        System.out.println(jwt);
        String email = jwtService.extractUsername(jwt);
//        System.out.println(email);
//        sessionID = session.getId();
        return ResponseEntity.ok().body(fileService.processInput(file, email));
    }

    @GetMapping("/download/{type}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("type") String type, HttpSession session, @RequestHeader(name = "Authorization") String token) {

        String jwt;
//        System.out.println(token);
        jwt = token.substring(7);
//        System.out.println(jwt);
        String email = jwtService.extractUsername(jwt);
//        System.out.println(email);

        String path = fileService.processOutput(type, email);
        return fileService.createResponse(path, email);
    }

}
