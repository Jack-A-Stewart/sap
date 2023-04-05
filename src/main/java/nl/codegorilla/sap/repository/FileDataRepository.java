package nl.codegorilla.sap.repository;

import nl.codegorilla.sap.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
}
