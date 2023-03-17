package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class WebReader implements FileHandler {

    @Override
    public List<MailCourseStatus> read(MultipartFile file) {
        return null;
    }

    @Override
    public String write(List<MailCourseStatus> list) {
        return null;
    }
}
