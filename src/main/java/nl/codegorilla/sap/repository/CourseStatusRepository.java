package nl.codegorilla.sap.repository;

import nl.codegorilla.sap.model.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseStatusRepository extends JpaRepository<CourseStatus, Long> {


    Optional<CourseStatus> findCourseStatusByStudentId(Long id);

}
