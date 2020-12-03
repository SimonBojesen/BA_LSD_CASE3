package booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication =
                new SpringApplicationBuilder()
                        .sources(Main.class)
                        .web(WebApplicationType.NONE)
                        .build();

        //springApplication.run(args);
        SpringApplication.run(Main.class);
        RMIRegistry.createRegistry();
    }
}
