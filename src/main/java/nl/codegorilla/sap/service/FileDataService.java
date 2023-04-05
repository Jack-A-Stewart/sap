package nl.codegorilla.sap.service;

import jakarta.transaction.Transactional;
import nl.codegorilla.sap.model.FileData;
import nl.codegorilla.sap.repository.FileDataRepository;
import org.springframework.stereotype.Service;

@Service
public class FileDataService {

    private final FileDataRepository fileDataRepository;

    public FileDataService(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    public FileData addFileData(FileData fileData) {
        return fileDataRepository.save(fileData);
    }

    @Transactional
    public void deleteFileData(Long id) {
        fileDataRepository.deleteById(id);
    }
}
