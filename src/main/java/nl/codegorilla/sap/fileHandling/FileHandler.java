package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandler {
    List<MailCourseStatus> read(MultipartFile file);
    String write(List<MailCourseStatus> data);
}
