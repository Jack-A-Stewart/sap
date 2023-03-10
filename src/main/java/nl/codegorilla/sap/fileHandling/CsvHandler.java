package nl.codegorilla.sap.fileHandling;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import nl.codegorilla.sap.service.CourseStatusService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvHandler implements FileHandler {

    private final CourseStatusService courseStatusService;

    public CsvHandler(CourseStatusService courseStatusService) {
        this.courseStatusService = courseStatusService;
    }

    @Override
    public List<String[]> readCsvFile(MultipartFile file) throws IOException, CsvException {
//
        InputStream inputStream = file.getInputStream(); // obtain an InputStream object from the MultipartFile
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        CSVReader csvReader = new CSVReader(reader);
        List<String[]> data = csvReader.readAll();

        List<String[]> updatedData = new ArrayList<>();

        for (String[] strings : data) {
            updatedData.add(courseStatusService.csvCheck(strings));
        }
        return updatedData;


    }

    @Override
    public void write(List<String[]> data) {

    }

    //    // need filepath??
    public ResponseEntity<?> write(List<String[]> data, MultipartFile file) throws IOException {
        FileWriter writer = new FileWriter(file.getName());
        CSVWriter csvWriter = new CSVWriter(writer);
        csvWriter.writeAll(data);


        // Convert the data to a CSV string
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter2 = new CSVWriter(stringWriter);
        csvWriter2.writeAll(data);
        String csv = stringWriter.toString();

        // Set the content type and headers
        ByteArrayResource resource = new ByteArrayResource(csv.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

        // Return the response object
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
//}

