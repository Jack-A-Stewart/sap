package nl.codegorilla.sap.fileHandling;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandler {
    List<String[]> readCsvFile(MultipartFile file);

    void write(MultipartFile content);

}

