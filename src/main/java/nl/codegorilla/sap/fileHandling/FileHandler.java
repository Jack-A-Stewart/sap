package nl.codegorilla.sap.fileHandling;

import com.opencsv.exceptions.CsvException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileHandler {
    List<String[]> readCsvFile(MultipartFile file) throws IOException, CsvException;

    void write(List<String[]> data);

}

