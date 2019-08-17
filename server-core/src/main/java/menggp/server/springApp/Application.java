package menggp.server.springApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"springApp","services","controllers"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } // end_main

} // end_class
