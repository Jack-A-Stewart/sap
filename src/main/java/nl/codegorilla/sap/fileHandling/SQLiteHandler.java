package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.exception.InvalidFileException;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.sqlite.SQLiteDataSource;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler implements FileHandler {


    SQLiteDataSource dataSource;
    String url = "";

    String path = "";

    public SQLiteHandler() {
        this.dataSource = new SQLiteDataSource();
    }

    @Override
    public List<MailCourseStatus> read(MultipartFile file) {
        if (file.isEmpty()) throw new InvalidFileException("File is empty");

        this.url = "jdbc:sqlite:" + file.getOriginalFilename();
        this.path = file.getOriginalFilename();

        assert path != null;
        File temp = new File(path);

        try (OutputStream os = new FileOutputStream(temp)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<MailCourseStatus> list = new ArrayList<>();
        String sql = "SELECT * FROM MailCourse";

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String email = result.getString("email");
                String course = result.getString("course");
                MailCourseStatus mailCourseStatus = new MailCourseStatus();
                mailCourseStatus.setEmail(email);
                mailCourseStatus.setCourse(course);
                list.add(mailCourseStatus);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    @Override
    public String write(List<MailCourseStatus> list) {
        String sql = "INSERT INTO MailCourse (status) VALUES (?)";
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (MailCourseStatus mailCourseStatus : list) {
                ps.setString(1, mailCourseStatus.getStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return path;
    }
}
