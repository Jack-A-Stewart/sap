package nl.codegorilla.sap.controller;

import nl.codegorilla.sap.model.CourseStatusInput;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/graduated")
public class IsGraduatedController {

    private final CourseStatusService courseStatusService;


    @Autowired
    public IsGraduatedController(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

//    @PostMapping("/isGraduated")
//    public ResponseEntity<?> isGraduated(@RequestBody CourseStatusInput courseStatusInput) {
//        return courseStatusService.isGraduated(courseStatusInput);
//    }

    @GetMapping("/test")
    public CourseStatusInput testResponse() {
        return new CourseStatusInput("bobcat@meow.com", "Bootcamp");

    }

//    @PutMapping("/tutorials/{id}")
//    public ResponseEntity<CourseStatus> updateTutorial(@PathVariable("id") long id, @RequestBody CourseStatus tutorial) {
//        Optional<CourseStatus> tutorialData = courseStatusService.findById(id);2
//
//        if (tutorialData.isPresent()) {
//            Tutorial _tutorial = tutorialData.get();
//            _tutorial.setTitle(tutorial.getTitle());
//            _tutorial.setDescription(tutorial.getDescription());
//            _tutorial.setPublished(tutorial.isPublished());
//            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
