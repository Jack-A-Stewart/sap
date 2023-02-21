package nl.codegorilla.sap.repository;

import nl.codegorilla.sap.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseStatusRepository extends JpaRepository<CourseStatus, Long> {


    Optional<CourseStatus> findCourseStatusByStudentId(Long id);

    Optional<CourseStatus> findCourseStatusByStudentIdAndCourseId(Long studentId, Long courseId);

    // SELECT COURSE_ID FROM COURSE_STATUS WHERE STUDENT_ID = 1

    List<CourseStatus> findAllByStudentId(Long id);
}
