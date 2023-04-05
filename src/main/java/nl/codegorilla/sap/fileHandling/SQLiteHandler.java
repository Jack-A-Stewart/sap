package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.exception.InvalidFileException;
import nl.codegorilla.sap.model.MailCourseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLiteHandler implements FileHandler {


    SQLiteDataSource dataSource;
    String url = "";

    String path = "";

    public SQLiteHandler() {
        dataSource = new SQLiteDataSource();
    }

    @Override
    public List<MailCourseStatus> read(MultipartFile file) {
        if (file.isEmpty()) throw new InvalidFileException("File is empty");
        setFile();
        dataSource.setUrl(url);

        File temp = new File(path);
        System.out.println(temp.getAbsolutePath());
        System.out.println(temp.getName());

        try (OutputStream os = new FileOutputStream(temp)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<MailCourseStatus> list = new ArrayList<>();
        String sql = "SELECT * FROM MailCourse";


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
        } finally {
            try {
                Files.delete(Paths.get(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }


    @Override
    public String write(List<MailCourseStatus> list) {
        String sql = "INSERT INTO MailCourse (email, course, status) VALUES (?, ?, ?)";
        setFile();
        dataSource.setUrl(url);

        createNewDatabase();
        initialiseDB();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (MailCourseStatus mailCourseStatus : list) {
                ps.setString(1, mailCourseStatus.getEmail());
                ps.setString(2, mailCourseStatus.getCourse());
                ps.setString(3, mailCourseStatus.getStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return path;
    }


    public String generateRandomString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(rand.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private void setFile() {
        // must be different for each file
        this.path = generateRandomString(6) + ".db";

        this.url = "jdbc:sqlite:" + path;
    }

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(dataSource.getUrl())) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                Logger.getLogger(SQLiteHandler.class.getName()).log(Level.INFO, "The driver name is " + meta.getDriverName());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initialiseDB() {
        String sql = """
                CREATE TABLE IF NOT EXISTS MailCourse (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 email TEXT,
                 course TEXT,
                 status TEXT
                );""";
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(SQLiteHandler.class.getName()).log(Level.WARNING, "Database creation failed...");
            System.out.println(e.getMessage());
        }
    }

}
