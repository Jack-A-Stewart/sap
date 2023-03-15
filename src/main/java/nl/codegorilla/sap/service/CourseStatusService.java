package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.exception.CourseNotFoundException;
import nl.codegorilla.sap.exception.StudentNotFoundException;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.MailCourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.model.dto.MailStatusDTO;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CourseStatusService {

    private final StudentService studentService;


    private final CourseStatusRepository courseStatusRepository;

    private final CourseService courseService;


    public CourseStatusService(StudentService studentService, CourseStatusRepository courseStatusRepository, CourseService courseService) {
        this.studentService = studentService;
        this.courseStatusRepository = courseStatusRepository;
        this.courseService = courseService;
    }

    public List<CourseStatus> findAllCoursesStatus() {
        return courseStatusRepository.findAll();
    }

    public CourseStatus addCourseStatus(CourseStatus courseStatus) {
        return courseStatusRepository.save(courseStatus);
    }

    @Transactional
    public void deleteCourseById(Long id) {
        courseStatusRepository.deleteById(id);
    }

    public MailStatusDTO isGraduated(CourseStatusInputDTO courseStatusInput) {
        MailStatusDTO mailStatusDTO = new MailStatusDTO(courseStatusInput.getEmail());
        Student student;
        Course course;
        try {
            student = studentService.findStudentByEmail(courseStatusInput.getEmail());
            course = courseService.findCourseByName(courseStatusInput.getCourseName());
        } catch (Exception exception) {
            mailStatusDTO.setEmail(courseStatusInput.getEmail());
            return mailStatusDTO;
        }

        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.getId(), course.getId());

        if (courseStatus.isPresent() && "graduated".equalsIgnoreCase(courseStatus.get().getStatus())) {
            mailStatusDTO.setStatus("true");
            return mailStatusDTO;
        }

        return mailStatusDTO;
    }

    public CourseStatus addCourseStatus(CourseNameStatusDTO courseNameStatus) {
        Student student = studentService.findStudentById(courseNameStatus.getId());
        Course course = courseService.findCourseByName(courseNameStatus.getCourseName());
        String status = courseNameStatus.getStatus();

        CourseStatus courseStatus = new CourseStatus(student, course, status);
        courseStatusRepository.save(courseStatus);
        return courseStatus;
    }

    public List<CourseNameStatusDTO> courseNameStatusList(Long id) {
        List<CourseStatus> courseStatusList = courseStatusRepository.findAllByStudentId(id);

        List<CourseNameStatusDTO> courseNameStatusList = new ArrayList<>();

        for (CourseStatus courseStatus : courseStatusList) {
            courseNameStatusList.add(new CourseNameStatusDTO(courseStatus.getId().getCourseId(), courseStatus.getCourse().getName(), courseStatus.getStatus()));
        }

        return courseNameStatusList;
    }

    public MailCourseStatus csvCheck(MailCourseStatus mailCourseStatus) {

        Student student;
        Course course;

        try {
            student = studentService.findStudentByEmail(mailCourseStatus.getEmail());
            course = courseService.findCourseByName(mailCourseStatus.getCourse());
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            mailCourseStatus.setStatus("Unknown");
            System.out.println(e.getMessage());
            return mailCourseStatus;
        }

        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.getId(), course.getId());

        if (courseStatus.isPresent()) {
            mailCourseStatus.setStatus(courseStatus.get().getStatus());
        } else {
            mailCourseStatus.setStatus("Unknown");
            Logger.getLogger(CourseStatusService.class.getName()).log(
                    Level.INFO, "Course status not found.");
        }
        return mailCourseStatus;
    }

    public List<MailCourseStatus> csvCheckMultipleCourses(MailCourseStatus mailCourseStatus) {
        Student student = new Student();
        List<CourseStatus> list = new ArrayList<>();
        List<MailCourseStatus> mailCourseStatuses = new ArrayList<>();

        try {
            student = studentService.findStudentByEmail(mailCourseStatus.getEmail());
            list = courseStatusRepository.findAllByStudentId(student.getId());
        } catch (StudentNotFoundException | CourseNotFoundException e) {
            mailCourseStatus.setStatus("Unknown");
            System.out.println(e.getMessage());
            mailCourseStatuses.add(mailCourseStatus);
            return mailCourseStatuses;
        }


        for (CourseStatus courseStatus : list) {
            MailCourseStatus mailCourseStatus1 = new MailCourseStatus();
            mailCourseStatus1.setEmail(student.getEmail());
            mailCourseStatus1.setCourse(courseService.findCourseById(courseStatus.getId().getCourseId()).getName());
            mailCourseStatus1.setStatus(courseStatus.getStatus());
            mailCourseStatuses.add(mailCourseStatus1);

        }
        return mailCourseStatuses;
    }

}
