package nl.codegorilla.sap.repository;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData, Long> {


//    public FileData findFileDataBySessionId(String sessionId);

    public FileData findFileDataByUserEmail(String userEmail);

    @Transactional
    public void deleteByUserEmail(String userEmail);

}
