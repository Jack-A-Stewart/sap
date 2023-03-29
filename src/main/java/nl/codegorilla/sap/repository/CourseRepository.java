package nl.codegorilla.sap.repository;

import nl.codegorilla.sap.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseById(Long id);

    Optional<Course> findCourseByName(String name);

}
