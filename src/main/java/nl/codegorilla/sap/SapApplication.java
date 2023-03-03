package nl.codegorilla.sap;

import nl.codegorilla.sap.model.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SapApplication implements CommandLineRunner {

    @Autowired
    private YAMLConfig yamlConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SapApplication.class);
        app.run();
    }

    public void run(String... args) throws Exception {
        System.out.println("URL: " + yamlConfig.getUrl());
        System.out.println("User: " + yamlConfig.getUser());
        System.out.println("Password: " + yamlConfig.getPassword());
    }

}

