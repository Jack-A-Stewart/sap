package nl.codegorilla.sap.service;

import nl.codegorilla.sap.fileHandling.FileHandler;
import nl.codegorilla.sap.fileHandling.FileHandlerFactory;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final CourseStatusService courseStatusService;


    public FileService(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    public String process(MultipartFile file) {
        String type = file.getContentType();

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
}
