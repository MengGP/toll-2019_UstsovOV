package menggp.server.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("menggp.server.config")
public class Application {

    // точка входа в приложение
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    } // end_main

} //end_class
