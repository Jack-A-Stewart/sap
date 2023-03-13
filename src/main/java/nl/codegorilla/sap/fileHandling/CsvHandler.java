package nl.codegorilla.sap.fileHandling;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import nl.codegorilla.sap.exception.InvalidFileException;
import nl.codegorilla.sap.model.MailCourseStatus;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CsvHandler implements FileHandler {

    private final CourseStatusService courseStatusService;

    public CsvHandler(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    public String csvHandler(MultipartFile file) {
        List<MailCourseStatus> list = read(file);

        List<MailCourseStatus> updatedList = new ArrayList<>();

        // check if null

        for (MailCourseStatus mailCourseStatus : list) {
            updatedList.add(courseStatusService.csvCheck(mailCourseStatus));
        }

        // check if exception happened

        return write(updatedList);
    }

    public List<MailCourseStatus> read(MultipartFile file) {

        if (file.isEmpty()) throw new InvalidFileException("File is empty");

        List<MailCourseStatus> list = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            HeaderColumnNameMappingStrategy<MailCourseStatus> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(MailCourseStatus.class);

            CsvToBean<MailCourseStatus> csvToBean = new CsvToBeanBuilder<MailCourseStatus>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            list = csvToBean.parse();

            for (MailCourseStatus mailCourseStatus : list) {
                mailCourseStatus.setEmail(mailCourseStatus.getEmail().trim());
                mailCourseStatus.setCourse(mailCourseStatus.getCourse().trim());
            }

            list.forEach(System.out::println);

            return list;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return list;
        }
    }

    public String write(List<MailCourseStatus> list) {
        String path = "temp.csv";
        Path myPath = Paths.get(path);

        try (BufferedWriter writer = Files.newBufferedWriter(myPath, StandardCharsets.UTF_8)) {

            StatefulBeanToCsv<MailCourseStatus> beanToCsv = new StatefulBeanToCsvBuilder<MailCourseStatus>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(list);
            return path;

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException |
                 IOException ex) {
            Logger.getLogger(CsvHandler.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
            return "Exception";
        }
    }
}


