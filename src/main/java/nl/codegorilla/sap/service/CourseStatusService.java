package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.Course;
import nl.codegorilla.sap.model.CourseStatus;
import nl.codegorilla.sap.model.Student;
import nl.codegorilla.sap.model.dto.CourseNameStatusDTO;
import nl.codegorilla.sap.model.dto.CourseStatusInputDTO;
import nl.codegorilla.sap.model.dto.MailStatusDTO;
import nl.codegorilla.sap.repository.CourseStatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // separate steps for dealing with csv (or any kind of files) reading/writing files for example


    public String[] csvCheck(String[] data) {
        String[] strings = new String[3];
        strings[0] = data[0];
        strings[1] = data[1];


        Student student;
        Course course;
        try {
            student = studentService.findStudentByEmail(data[0]);
            course = courseService.findCourseByName(data[1]);
        } catch (Exception exception) {

            strings[2] = "Unknown";
            return strings;
        }

        Optional<CourseStatus> courseStatus = courseStatusRepository.findCourseStatusByStudentIdAndCourseId(student.getId(), course.getId());
        data[2] = String.valueOf(courseStatus);
        if (courseStatus.isPresent()) {
            strings[2] = courseStatus.get().getStatus();
        } else {
            strings[2] = "Unknown";
        }
        return strings;
    }
}
