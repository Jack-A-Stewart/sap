package nl.codegorilla.sap.service;

import nl.codegorilla.sap.exception.ServerException;
import nl.codegorilla.sap.fileHandling.FileHandler;
import nl.codegorilla.sap.fileHandling.FileHandlerFactory;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final CourseStatusService courseStatusService;

    List<MailCourseStatus> statusList;

    public FileService(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }


    public List<String> processInput(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String type = FilenameUtils.getExtension(filename);

        List<MailCourseStatus> list;

        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.createFileHandler(type);

        list = fileHandler.read(file);

        statusList = new ArrayList<>();

        for (MailCourseStatus mailCourseStatus : list) {
            statusList.add(courseStatusService.addStatus(mailCourseStatus));
        }
        return List.of("csv", "db");
    }

    public String processOutput(String type) {
        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.createFileHandler(type);

        return fileHandler.write(statusList);
    }


    public ResponseEntity<ByteArrayResource> createResponse(String filePath) {
        Path path = Paths.get(filePath);
        String type = "";

        try {
            type = Files.probeContentType(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path);
            headers.add(HttpHeaders.CONTENT_TYPE, type);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException e) {
            throw new ServerException("Something went wrong on the server");
        } finally {
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
