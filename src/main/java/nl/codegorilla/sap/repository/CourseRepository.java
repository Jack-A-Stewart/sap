package nl.codegorilla.sap.repository;

import nl.codegorilla.sap.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {


    void deleteById(Long id);

}