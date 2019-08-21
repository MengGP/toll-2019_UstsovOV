package menggp.server.security;

import org.omg.CORBA.ObjectHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan("menggp.server.config")
public class Application {

    // точка входа в приложение
    public static void main(String[] args) throws Throwable {

        HashMap<String,Object> props = new HashMap<>();
        props.put("server.port",8088);

        //SpringApplication.run(Application.class, args);
        // запускаем в виде SpringBootApplication - но без поднятия web-cервера - т.к. для реализации функционала он не нужен
        // - т.к. для реализации функционала он не нужен и бужет мешать web-серверу проекта server-core
        new SpringApplicationBuilder(Application.class)
                //.web(false) // но это не точно
                .properties(props)
                .run(args);
        // обычный запуск
        //SpringApplication.run(Application.class, args);

    } // end_main

} //end_class
