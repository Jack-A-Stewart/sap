package nl.codegorilla.sap.service;

import nl.codegorilla.sap.exception.ServerException;
import nl.codegorilla.sap.fileHandling.FileHandler;
import nl.codegorilla.sap.fileHandling.FileHandlerFactory;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


    public FileService(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    public String process(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String type = FilenameUtils.getExtension(filename);

        List<MailCourseStatus> list;

        FileHandlerFactory fileHandlerFactory = new FileHandlerFactory();
        FileHandler fileHandler = fileHandlerFactory.createFileHandler(type);

        list = fileHandler.read(file);

        List<MailCourseStatus> updatedList = new ArrayList<>();

        for (MailCourseStatus mailCourseStatus : list) {
            updatedList.add(courseStatusService.addStatus(mailCourseStatus));
        }
        return fileHandler.write(updatedList);
    }


    public ResponseEntity<?> createResponse(String filePath, MultipartFile file) throws IOException {
        Path path = Paths.get(filePath);
        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path);
            headers.add(HttpHeaders.CONTENT_TYPE, file.getContentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);

        } catch (IOException e) {
            throw new ServerException("Something went wrong on the server");
        } finally {
            Files.delete(path);
        }
    }










}
