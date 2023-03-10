package nl.codegorilla.sap.fileHandling;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.codegorilla.sap.model.TempFileObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHandler implements FileHandler {


    @Override
    public List<String[]> readCsvFile(MultipartFile file) {
//
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<TempFileObject> result = new CsvToBeanBuilder<TempFileObject>(csvReader).withType(TempFileObject.class).build().parse();
            System.out.println(result);


        } catch (IOException exception) {
            System.out.println("stuff went wrong");
        }
return null;

    }

    @Override
    public void write(MultipartFile content) {

    }


    // need filepath??
    public void write(List<String[]> data, String filename, String extraColumnData) throws IOException {
        FileWriter writer = new FileWriter(filename);
        CSVWriter csvWriter = new CSVWriter(writer);

        List<String[]> rowsWithExtraColumn = new ArrayList<>();
        for (String[] row : data) {
            String[] rowWithExtraColumn = Arrays.copyOf(row, row.length + 1);
            rowWithExtraColumn[rowWithExtraColumn.length - 1] = extraColumnData;
            rowsWithExtraColumn.add(rowWithExtraColumn);
        }

        csvWriter.writeAll(rowsWithExtraColumn);

        csvWriter.close();
        writer.close();
    }
}

