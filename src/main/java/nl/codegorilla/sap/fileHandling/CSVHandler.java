package nl.codegorilla.sap.fileHandling;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import nl.codegorilla.sap.exception.InvalidFileException;
import nl.codegorilla.sap.exception.ServerException;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static nl.codegorilla.sap.utility.Utility.getAllFieldsAsStringArray;


@Service
public class CSVHandler implements FileHandler {

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

            List<MailCourseStatus> trimmedList;

            if (list.get(0).getEmail() == null) {
                throw new InvalidFileException("This file doesn't contain an email address");
            }

            if (list.get(0).getCourse() == null) {
                // If a .csv contains only a mail address
                trimmedList = trimSpaceMail(list);

            } else {
                // If a .csv file contains both an Email and Course
                trimmedList = trimSpaceMailCourse(list);
            }

            return trimmedList;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return list;
        }
    }

    public String write(List<MailCourseStatus> list) {
        String path = "temp.csv";
        Path myPath = Paths.get(path);
        String[] headers = getAllFieldsAsStringArray(MailCourseStatus.class);

        try (BufferedWriter writer = Files.newBufferedWriter(myPath, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(writer)) {

            csvWriter.writeNext(headers);

            ColumnPositionMappingStrategy<MailCourseStatus> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(MailCourseStatus.class);
            mappingStrategy.setColumnMapping(headers);

            StatefulBeanToCsv<MailCourseStatus> beanToCsv = new StatefulBeanToCsvBuilder<MailCourseStatus>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .build();

            beanToCsv.write(list);

            return path;

        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
            throw new ServerException("The CSV file could not be read");
        } catch (IOException e) {
            throw new ServerException("An IOException occurred on the server");
        }
    }

    public List<MailCourseStatus> trimSpaceMail(List<MailCourseStatus> list) {
        for (MailCourseStatus mailCourseStatus : list) {
            mailCourseStatus.setEmail(mailCourseStatus.getEmail().trim());
        }
        return list;
    }

    public List<MailCourseStatus> trimSpaceMailCourse(List<MailCourseStatus> list) {
        for (MailCourseStatus mailCourseStatus : list) {
            mailCourseStatus.setEmail(mailCourseStatus.getEmail().trim());
            mailCourseStatus.setCourse(mailCourseStatus.getCourse().trim());
        }
        return list;
    }


}


