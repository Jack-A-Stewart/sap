package nl.codegorilla.sap.fileHandling;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class WebReader implements FileHandler {


    @Override
    public List<String[]> readCsvFile(MultipartFile file) {
        return null;
    }

    @Override
    public void write(MultipartFile content) {

    }
}
