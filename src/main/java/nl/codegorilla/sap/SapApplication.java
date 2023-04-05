package nl.codegorilla.sap;

import nl.codegorilla.sap.model.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class SapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SapApplication.class, args);
    }

}

