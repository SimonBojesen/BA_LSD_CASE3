package booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication =
                new SpringApplicationBuilder()
                        .sources(Main.class)
                        .web(WebApplicationType.NONE)
                        .build();
        System.setProperty("java.rmi.server.hostname","207.154.197.222");
        //springApplication.run(args);
        SpringApplication.run(Main.class);
        RMIRegistry.createRegistry();
    }
}
